@echo off
if not exist out mkdir out
javac -d out src\retailstore\Main.java src\retailstore\model\*.java src\retailstore\service\*.java src\retailstore\exception\*.java
if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)
java -cp out retailstore.Main
pause
