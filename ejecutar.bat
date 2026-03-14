@echo off
echo ========================================
echo Ejecutando Antivirus Educativo 2025
echo ========================================
echo.

REM Configurar Java Home
set JAVA_HOME=C:\Program Files\Apache NetBeans\jdk
set PATH=%JAVA_HOME%\bin;%PATH%

echo Compilando el proyecto...
javac --module-path "C:\Program Files\Apache NetBeans\javafx\lib" ^
      --add-modules javafx.controls ^
      -d target\classes ^
      src\main\java\module-info.java ^
      src\main\java\com\mycompany\antiviruscbd\*.java ^
      src\main\java\com\mycompany\antiviruscbd\database\*.java ^
      src\main\java\com\mycompany\antiviruscbd\task\*.java ^
      src\main\java\com\mycompany\antiviruscbd\ui\*.java

if %ERRORLEVEL% NEQ 0 (
    echo Error al compilar
    pause
    exit /b 1
)

echo.
echo Compilacion exitosa!
echo Ejecutando aplicacion...
echo.

java --module-path "C:\Program Files\Apache NetBeans\javafx\lib;target\classes" ^
     --add-modules javafx.controls ^
     -m com.mycompany.antiviruscbd/com.mycompany.antiviruscbd.App

pause
