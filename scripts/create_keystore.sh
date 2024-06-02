#!/bin/sh

# This script creates a keystore.jks file that contains the self-signed HTTPS certificate for development usage
# through the proxy. The keystore.jks file is required by the services to trust the self-signed certificate
# while accessing local keycloak auth server.

CERT_PATH=./certificates/site.crt
JAVA_KEYSTORE_PATH=/src/main/resources/keystore.jks

if [ ! -f create_keystore.sh ]; then
    echo "Use the scripts directory to run this script. Like: cd scripts && ./create_keystore.sh"
    exit 1
fi

if [ ! -f $CERT_PATH ]; then
    echo "Certificate file is not found. Please make sure you have run generate_ssl_certs.sh script \
in the scripts directory."
    exit 1
fi

rm -f keystore.jks

# Create a keystore
echo "Creating keystore.jks"
keytool -genkey -alias myalias -keyalg RSA -keystore keystore.jks -keysize 4096 -validity 3650 -dname "CN=localhost, OU=Development, O=ETDEVELOPS, L=Cody, S=Wyoming, C=US" -storepass changeit

# Import the certificate
echo "Importing certificate"
keytool -import -alias site -keystore keystore.jks -file $CERT_PATH -storepass changeit -noprompt

# Copy the keystore to the services resource directory
echo "Copying keystore to services resource directory"
cp keystore.jks ../services/core${JAVA_KEYSTORE_PATH}

rm keystore.jks