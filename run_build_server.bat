@echo off

:: Step 1: Navigate to the project directory (where the batch file is located)
cd /d %~dp0

:: Step 2: Build with Gradle
echo Building project with Gradle...
call gradlew build --stacktrace --info
if %errorlevel% neq 0 exit /b %errorlevel%

:: Step 3: Copy JAR to Server
echo Copying JAR to Server...
powershell.exe -Command "Copy-Item -Force '.\build\libs\*' '.\Server\plugins\'"
if %errorlevel% neq 0 exit /b %errorlevel%

:: Step 4: Run the Server
echo Starting server...
cd Server
java -jar paper-1.21-130.jar nogui
if %errorlevel% neq 0 exit /b %errorlevel%

echo Pipeline finished successfully!
exit /b 0
