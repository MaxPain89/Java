SET BASEDIR=%~dp0
SET BASEDIR=%BASEDIR:~0,-1%

nvm use 7.5.0 && ^
timeout /t 2 && ^
cd %BASEDIR%\admin-ui && ^
ng build --delete-output-path --prod --output-path %BASEDIR%\src\main\resources\static && ^
cd %BASEDIR% && ^
mvn clean verify
