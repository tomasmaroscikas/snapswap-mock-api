#!/bin/bash
#
# Create CA key and sertificate
# Create server key and certificate request
# sign server sertificate request with CA certificate
# convert server certificate to PKC #12 format
# Based on article
# https://blog.devolutions.net/2020/07/tutorial-how-to-generate-secure-self-signed-server-and-client-certificates-with-openssl/
#

openssl ecparam -name prime256v1 -genkey -noout -out ca.key

openssl req -new -x509 -sha256 -key ca.key -out ca.crt

openssl ecparam -name prime256v1 -genkey -noout -out server.key

openssl req -new -sha256 -key server.key -out server.csr

openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt -days 1000 -sha256

openssl pkcs12 -export -in server.crt -inkey server.key -out server.p12 -name micronaut -chain -CAfile ca.crt -caname root
