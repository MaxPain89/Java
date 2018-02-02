SET BASEDIR=%~dp0
SET BASEDIR=%BASEDIR:~0,-1%

if [%1]==[] goto usage
java -jar "%BASEDIR%\target\mdb_access-1.0-SNAPSHOT-spring-boot.jar" %1
goto :eof
:usage

@echo Usage: %0 ^<path to .mdb file^>
exit /B 1
