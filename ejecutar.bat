@echo off
echo ===================================
echo  Sistema de Gestion de Formula 1
echo ===================================
echo.

REM Verificar si existe el directorio bin
if not exist "bin" (
    echo Creando directorio bin...
    mkdir bin
)

echo Compilando proyecto...
javac -d bin -sourcepath src src\vista\VentanaPrincipal.java

if %ERRORLEVEL% EQU 0 (
    echo Compilacion exitosa!
    echo Ejecutando aplicacion...
    echo.
    java -cp bin vista.VentanaPrincipal
) else (
    echo Error en la compilacion!
    pause
)