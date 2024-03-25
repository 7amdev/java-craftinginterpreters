@echo off

REM adds current directory(.) and (build) folder to classpath
set CLASSPATH=.;build

if [%1%] == [] goto error 

rmdir /S /Q build
mkdir build
javac -d build %1%.java
java -cp %CLASSPATH% %1%
goto end

:error
echo "error: run <program_name>"

:end