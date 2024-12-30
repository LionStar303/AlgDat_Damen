@echo off
cd /d "%~dp0"  REM Change to the directory of the batch file
start "" .\run_build_server.bat
cd /d "%~dp0Minecraft"  
.\portablemc.exe --work-dir .\ --main-dir .\ -s localhost

pause
