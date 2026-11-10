@echo off
REM =====================================================================
REM Campus Feedback System - One-Click Build & Run Script (Windows CMD)
REM =====================================================================

title Campus Feedback System
echo ========================================================
echo       Compiling Campus Feedback System (Core Java)
echo ========================================================

REM Create bin directory if missing
if not exist "bin" (
    mkdir "bin"
)

REM Compile all Java packages into bin
javac -d bin src/exception/*.java src/model/*.java src/storage/*.java src/service/*.java src/util/*.java src/main/*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation failed. Please ensure JDK 17+ is installed and in PATH.
    pause
    exit /b %ERRORLEVEL%
)

echo [SUCCESS] Compilation complete. Launching application...
echo.

REM Run the main class
java -cp bin main.Main

echo.
pause

