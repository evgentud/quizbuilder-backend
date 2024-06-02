#!/bin/bash

CERT_DIR=./certificates
CERT_KEY=site.key
CERT_CSR=site.csr
CERT_CRT=site.crt

if [ ! -f generate_ssl_certs.sh ]; then
    echo "Use the scripts directory to run this script. Like: cd scripts && ./generate_ssl_certs.sh"
    exit 1
fi

if [ ! -d $CERT_DIR ]; then
    mkdir $CERT_DIR
fi

#Generate a private key
openssl genrsa -out "${CERT_DIR}/${CERT_KEY}" 4096

#Create a certificate signing request (CSR)
openssl req -new -key $CERT_DIR/$CERT_KEY -out $CERT_DIR/$CERT_CSR -sha256 -subj "/CN=localhost/OU=Development/O=MYORG/L=Cody/ST=Wyoming/C=US"

#Generate the self-signed certificate
openssl x509 -req -days 3650 -in $CERT_DIR/$CERT_CSR -signkey $CERT_DIR/$CERT_KEY -out $CERT_DIR/$CERT_CRT
