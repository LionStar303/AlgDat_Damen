@echo off
REM Überprüft, ob das winget-Installationsskript existiert und führt es aus
if exist "winget-install.ps1" (
    powershell -ExecutionPolicy Bypass -File winget-install.ps1
) else (
    echo winget-install.ps1 nicht gefunden.
)

REM Führt die Java 21 Installation durch
if exist "Java21Check.ps1" (
    powershell -ExecutionPolicy Bypass -File Java21Check.ps1
) else (
    echo Java21Check.ps1 nicht gefunden.
)
