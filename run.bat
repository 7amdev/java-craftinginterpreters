@echo off

REM adds current directory(.) and (build) folder to classpath
set CLASSPATH=.;build
set CODEPATH=code

if [%1] == [] goto error 

rmdir /S /Q build
mkdir build
javac -d build %CODEPATH%\%1.java
java -cp %CLASSPATH% %CODEPATH%.%1 %2
goto end

:error
echo "error: run <program_name>"

:end