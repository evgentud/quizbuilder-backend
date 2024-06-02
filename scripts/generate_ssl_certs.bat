@echo off

REM Define variables
set OPENSSL_URL=https://slproweb.com/download/Win64OpenSSL_Light-3_3_0.exe
set OPENSSL_INSTALLER=Win64OpenSSL_Light-3_3_0.exe
set OPENSSL_DIR=C:\Program Files\OpenSSL-Win64
set CERT_DIR=%cd%\certificates
set CERT_KEY=site.key
set CERT_CSR=site.csr
set CERT_CRT=site.crt

if not exist generate_ssl_certs.bat (
    echo Use the scripts directory to run this script. Like: cd scripts then generate_ssl_certs.bat
    exit /b 1
)

if not exist "%CERT_DIR%" (
    mkdir "%CERT_DIR%"
)

REM Check if OpenSSL is already installed
if exist "%OPENSSL_DIR%\bin\openssl.exe" (
    echo OpenSSL is already installed. Skipping download and installation...
) else (
    REM Download OpenSSL
    echo Downloading OpenSSL...
    powershell -Command "Invoke-WebRequest -Uri %OPENSSL_URL% -OutFile %OPENSSL_INSTALLER%"
    if %errorlevel% neq 0 (
        echo Failed to download OpenSSL. Exiting...
        exit /b 1
    )

    REM Install OpenSSL silently
    echo Installing OpenSSL...
    start /wait %OPENSSL_INSTALLER% /silent /verysilent /suppressmsgboxes
    if %errorlevel% neq 0 (
        echo Failed to install OpenSSL. Exiting...
        exit /b 1
    )
)

REM Generate a private key
echo Generating private key...
"%OPENSSL_DIR%\bin\openssl" genrsa -out %CERT_DIR%\%CERT_KEY% 4096
if %errorlevel% neq 0 (
    echo Failed to generate private key. Exiting...
    exit /b 1
)

REM Create a certificate signing request (CSR)
echo Creating certificate signing request...
"%OPENSSL_DIR%\bin\openssl" req -new -key %CERT_DIR%\%CERT_KEY% -out %CERT_DIR%\%CERT_CSR% -sha256 -subj "/CN=localhost/OU=Development/O=MYORG/L=Cody/ST=Wyoming/C=US"
if %errorlevel% neq 0 (
    echo Failed to create certificate signing request. Exiting...
    exit /b 1
)

REM Generate the self-signed certificate
echo Generating self-signed certificate...
"%OPENSSL_DIR%\bin\openssl" x509 -req -days 3650 -in %CERT_DIR%\%CERT_CSR% -signkey %CERT_DIR%\%CERT_KEY% -out %CERT_DIR%\%CERT_CRT%
if %errorlevel% neq 0 (
    echo Failed to generate self-signed certificate. Exiting...
    exit /b 1
)

echo Certificate and key have been generated successfully.
echo Certificate: %CERT_DIR%\%CERT_CRT%
echo Private Key: %CERT_DIR%\%CERT_KEY%

echo Done.
