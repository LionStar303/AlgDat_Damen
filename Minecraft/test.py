#! /usr/bin/env nix-shell
#! nix-shell -i python3 -p python3

import json
import os
import platform
import requests
from pathlib import Path
import subprocess


"""
Debug output
"""
def debug(str):
    if os.getenv('DEBUG') != None:
        print(str)

"""
[Gets the natives_string toprepend to the jar if it exists. If there is nothing native specific, returns and empty string]
"""
def get_natives_string(lib):
    arch = ""
    if platform.architecture()[0] == "64bit":
        arch = "64"
    elif platform.architecture()[0] == "32bit":
        arch = "32"
    else:
        raise Exception("Architecture not supported")

    nativesFile=""
    if not "natives" in lib:
        return nativesFile

    if "windows" in lib["natives"] and platform.system() == 'Windows':
        nativesFile = lib["natives"]["windows"].replace("${arch}", arch)
    elif "osx" in lib["natives"] and platform.system() == 'Darwin':
        nativesFile = lib["natives"]["osx"].replace("${arch}", arch)
    elif "linux" in lib["natives"] and platform.system() == "Linux":
        nativesFile = lib["natives"]["linux"].replace("${arch}", arch)
    else:
        raise Exception("Platform not supported")

    return nativesFile


"""
[Parses "rule" subpropery of library object, testing to see if should be included]
"""
def should_use_library(lib):
    def rule_says_yes(rule):
        useLib = None

        if rule["action"] == "allow":
            useLib = False
        elif rule["action"] == "disallow":
            useLib = True

        if "os" in rule:
            for key, value in rule["os"].items():
                os = platform.system()
                if key == "name":
                    if value == "windows" and os != 'Windows':
                        return useLib
                    elif value == "osx" and os != 'Darwin':
                        return useLib
                    elif value == "linux" and os != 'Linux':
                        return useLib
                elif key == "arch":
                    if value == "x86" and platform.architecture()[0] != "32bit":
                        return useLib

        return not useLib

    if not "rules" in lib:
        return True

    shouldUseLibrary = False
    for i in lib["rules"]:
        if rule_says_yes(i):
            return True

    return shouldUseLibrary

"""
[Get string of all libraries to add to java classpath]
"""
def get_classpath(lib, mcDir):
    cp = []

    def process_lib(i, mcDir):

        name = i["name"].split(":")
        libDomain = name[0]
        libName = name[1]
        libVersion = name[2] 
        jarPath = os.path.join(mcDir, "libraries", *
                               libDomain.split('.'), libName, libVersion)

        native = name[3] if len(name) > 3 else ""
        jarFile = libName + "-" + libVersion + ".jar"
        if native != "":
            jarFile = libName + "-" + libVersion + "-" + native + ".jar"

        path = os.path.join(jarPath, jarFile)
        if not os.path.isfile(path):
            result = requests.get(i["downloads"]["artifact"]["url"])
            p = os.path.join(mcDir, 'libraries', i["downloads"]["artifact"]["path"])
            os.makedirs(os.path.dirname(p), exist_ok=True)
            file = open(p, "w")
            file.write(result.text)
        return path

    
    for i in lib["libraries"]:
        if not should_use_library(i):
            continue
        cp.append(process_lib(i,mcDir))

    # for r in lib["requires"]:
    #     for i in json.loads(Path(os.path.join(mcDir, 'meta', r["uid"], f'{r["suggests"]}.json')).read_text())["libraries"]:
    #         if not should_use_library(i):
    #             print("rejected ",i)
    #             continue
    #         print("accepted ",i)
    #         cp.append(process_lib(i,mcDir))
    version = '1.21.1'
    cp.append(os.path.join(mcDir, 'versions', f'{version}', f'{version}.jar'))
   
    return os.pathsep.join(cp)

version = '1.21.1'
username = 'Testi'
uuid = 'd90c00f8-c9ab-443b-893b-56d94ff0569c'
accessToken = ''

mcDir = os.path.join('.')
nativesDir = os.path.join(mcDir, 'libraries')
clientJson = json.loads(Path(os.path.join(mcDir, 'versions', f'{version}', f'{version}.json')).read_text())
classPath = get_classpath(clientJson, mcDir)
mainClass = clientJson['mainClass']
versionType = clientJson['type']
assetIndex = clientJson['assetIndex']['id']

debug(classPath)
debug(mainClass)
debug(versionType)
debug(assetIndex)

path = ''
if(platform.system()=='Windows'):
    path = os.path.join(".", "winjdk23", "bin", "java.exe")   
elif(platform.system()=='Linux'):
    path = os.path.join(".", "linuxjdk23", "bin", "java")
elif(platform.system()=='Darwin'):
    path = os.path.join(".", "macjdk23", "Contents", "Home", "bin", "java")
elif(platform.system()=='TempleOS'):
    print("Consult Job 5:15")

subprocess.Popen([
    path,
    f'-Djava.library.path={nativesDir}',
    f'-Dorg.lwjgl.system.SharedLibraryExtractPath={nativesDir}',
    f'-Dorg.lwjgl.librarypath=/usr/lib/jni',
    '-Dorg.lwjgl.util.Debug=true',
    '-Dorg.lwjgl.util.DebugLoader=true',

    '-Dminecraft.launcher.brand=custom-launcher',
    '-Dminecraft.launcher.version=2.1',
    '-cp',
    classPath,
    'net.minecraft.client.main.Main',
    '--username',
    username,
    '--version',
    version,
    '--gameDir',
    mcDir,
    '--assetsDir',
    os.path.join(mcDir, 'assets'),
    '--assetIndex',
    assetIndex,
    '--uuid',
    uuid,
    '--accessToken',
    accessToken,
    '--userType',
    'mojang',
    '--versionType',
    'release'
    "--quickPlayMultiplayer",
    "localhost"
])

subprocess.Popen([
    path,
    "-jar",
    "paper-1.21-130.jar"
], cwd="../Server")

