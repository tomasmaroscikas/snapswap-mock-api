echo -n | openssl s_client -connect localhost:8443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > micronautcert.crt
keytool -delete -noprompt -alias "micronaut" -keystore /usr/local/Cellar/openjdk@11/11.0.15/libexec/openjdk.jdk/Contents/Home/lib/security/cacerts -storepass changeit
keytool -import -trustcacerts -keystore /usr/local/Cellar/openjdk@11/11.0.15/libexec/openjdk.jdk/Contents/Home/lib/security/cacerts -storepass changeit -noprompt -alias micronaut -file micronautcert.crt