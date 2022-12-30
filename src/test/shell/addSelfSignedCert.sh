#!/bin/bash

echo -n | openssl s_client -connect localhost:8443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > micronautcert.crt

#cacert_store=/usr/local/Cellar/openjdk@11/11.0.15/libexec/openjdk.jdk/Contents/Home/lib/security/cacerts
#cacert_store=/home/tomas/.sdkman/candidates/java/11.0.12-open/lib/security/cacerts

keytool -delete -noprompt -alias "micronaut" -trustcacerts -cacerts -storepass changeit
keytool -import -trustcacerts -cacerts -storepass changeit -noprompt -alias micronaut -file micronautcert.crt
