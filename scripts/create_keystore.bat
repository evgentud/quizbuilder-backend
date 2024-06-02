@echo off
REM This script creates a keystore.jks file that contains the self-signed HTTPS certificate for development usage
REM through the proxy. The keystore.jks file is required by the services to trust the self-signed certificate
REM while accessing local keycloak auth server.

SET CERT_PATH=.\certificates\site.crt
SET JAVA_KEYSTORE_PATH=\src\main\resources\keystore.jks

IF NOT EXIST create_keystore.bat (
    echo Use the scripts directory to run this script. Like: cd scripts then create_keystore.bat
    EXIT /B 1
)

IF NOT EXIST %CERT_PATH% (
    echo Certificate file is not found. Please make sure you have run generate_sample_certs.sh script in the docker\thirdparty\proxy\certs directory.
    EXIT /B 1
)

IF EXIST keystore.jks (
    DEL keystore.jks
)

REM Create a keystore
echo Creating keystore.jks
keytool -genkey -alias myalias -keyalg RSA -keystore keystore.jks -keysize 4096 -validity 3650 -dname "CN=localhost, OU=Development, O=ETDEVELOPS, L=Cody, S=Wyoming, C=US" -storepass changeit

REM Import the certificate
echo Importing certificate
keytool -import -alias site -keystore keystore.jks -file %CERT_PATH% -storepass changeit -noprompt

REM Copy the keystore to the services resource directory
echo Copying keystore to services resource directory
copy keystore.jks ..\services\core%JAVA_KEYSTORE_PATH%

DEL keystore.jks
