import json
import os
import platform
from pathlib import Path
import subprocess

def get_natives_string(lib):
    """
    Gets the natives_string to prepend to the jar if it exists.
    If there is nothing native specific, returns an empty string.
    """
    arch = ""
    if platform.architecture()[0] == "64bit":
        arch = "64"
    elif platform.architecture()[0] == "32bit":
        arch = "32"
    else:
        raise Exception("Architecture not supported")

    if "natives" not in lib:
        return ""

    natives_dict = {
        "windows": f"natives-windows-{arch}",
        "osx": f"natives-osx-{arch}",
        "linux": f"natives-linux-{arch}"
    }

    os_name = platform.system()
    if os_name in natives_dict:
        return natives_dict[os_name]
    else:
        raise Exception("Platform not supported")

def should_use_library(lib):
    """
    Parses the "rule" subproperty of the library object, testing to see if it should be included.
    """
    def rule_says_yes(rule):
        use_lib = None

        if rule["action"] == "allow":
            use_lib = False
        elif rule["action"] == "disallow":
            use_lib = True

        if "os" in rule:
            os_name = platform.system()
            arch = platform.architecture()[0]
            for key, value in rule["os"].items():
                if key == "name":
                    if value == "windows" and os_name != 'Windows':
                        return use_lib
                    elif value == "osx" and os_name != 'Darwin':
                        return use_lib
                    elif value == "linux" and os_name != 'Linux':
                        return use_lib
                elif key == "arch":
                    if value == "x86" and arch != "32bit":
                        return use_lib

        return not use_lib

    if "rules" not in lib:
        return True

    should_use_library = False
    for rule in lib["rules"]:
        if rule_says_yes(rule):
            return True

    return should_use_library

def get_classpath(lib, mc_dir):
    """
    Get the string of all libraries to add to the Java classpath.
    """
    cp = []

    for library in lib["libraries"]:
        if not should_use_library(library):
            continue

        name_parts = library["name"].split(":")
        lib_domain = name_parts[0]
        lib_name = name_parts[1]
        lib_version = name_parts[2]
        
        jar_path = os.path.join(mc_dir, "libraries", *lib_domain.split('.'), lib_name, lib_version)
        native = get_natives_string(library)
        jar_file = f"{lib_name}-{lib_version}.jar"
        if native:
            jar_file = f"{lib_name}-{lib_version}-{native}.jar"

        cp.append(os.path.join(jar_path, jar_file))

    cp.append(os.path.join(mc_dir, "versions", lib["id"], f'{lib["id"]}.jar'))

    return os.pathsep.join(cp)

version = '1.21.1'
username = '{username}'
uuid = '{uuid}'
access_token = '{token}'

mc_dir = os.path.join(os.getenv('APPDATA'), '.minecraft')
natives_dir = os.path.join(mc_dir, 'versions', version, 'natives')
client_json = json.loads(Path(os.path.join(mc_dir, 'versions', version, f'{version}.json')).read_text())
classpath = get_classpath(client_json, mc_dir)
main_class = client_json['mainClass']
version_type = client_json['type']
asset_index = client_json['assetIndex']['id']

subprocess.call([
    '/usr/bin/java',
    f'-Djava.library.path={natives_dir}',
    '-Dminecraft.launcher.brand=custom-launcher',
    '-Dminecraft.launcher.version=2.1',
    '-cp',
    classpath,
    main_class,
    '--username',
    username,
    '--version',
    version,
    '--gameDir',
    mc_dir,
    '--assetsDir',
    os.path.join(mc_dir, 'assets'),
    '--assetIndex',
    asset_index,
    '--uuid',
    uuid,
    '--accessToken',
    access_token,
    '--userType',
    'mojang',
    '--versionType',
    'release'
])