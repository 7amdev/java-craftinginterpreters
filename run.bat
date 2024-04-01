@echo off

REM TODO: add a flag to build generateAst

REM adds current directory(.) and (build) folder to classpath
set CLASSPATH=.;build
set CODEPATH=code
set TOOLPATH=tool

if [%1] == [] goto error 

rmdir /S /Q build
mkdir build
javac -d build %TOOLPATH%\generate_ast.java
javac -d build %CODEPATH%\*.java
java %TOOLPATH%.generate_ast %CODEPATH%
java -cp %CLASSPATH% %CODEPATH%.%1 %2
goto end

:error
echo "error: run <program_name>"

:end