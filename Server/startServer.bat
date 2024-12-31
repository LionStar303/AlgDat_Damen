echo Starting server...
cd Server
java -jar paper-1.21-130.jar nogui
if %errorlevel% neq 0 exit /b %errorlevel%