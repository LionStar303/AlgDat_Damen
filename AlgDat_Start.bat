@echo off
cd /d "%~dp0"  REM Change to the directory of the batch file
start "" .\Server\startServer.bat


cd /d "%~dp0Minecraft"  
.\portablemc.exe --work-dir .\ --main-dir .\ start fabric:1.21.1 -s localhost

pause
