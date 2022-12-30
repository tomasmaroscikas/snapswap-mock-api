package com.oonyy.jwt

import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.SignedJWT
import org.slf4j.LoggerFactory
import java.security.SecureRandom


class DossierJwtParser {

    companion object {
        private val logger = LoggerFactory.getLogger(DossierJwtParser::class.java)

        fun parse(jwtTokenString: String): DossierJwtPayload {
            val jwtParsed = SignedJWT.parse(removePrefix(jwtTokenString, "Bearer "))
            val payloadMap = jwtParsed.payload.toJSONObject()
            logger.debug("Token payload map $payloadMap")
            return DossierJwtPayload(
                payloadMap["dossierId"] as String,
                payloadMap["clientId"] as String,
                payloadMap["exp"] as Long,
                payloadMap["iat"] as Long,
                payloadMap["sub"] as String,
                payloadMap["iss"] as String
            )
        }

        fun createJwtToken(dossierJwtPayload: String): String {
            // Generate random 256-bit (32-byte) shared secret
            val random = SecureRandom()
            val sharedSecret = ByteArray(32)
            random.nextBytes(sharedSecret)
            // Create HMAC signer
            val signer: JWSSigner = MACSigner(sharedSecret)
            // Prepare JWS object with "Hello, world!" payload
            val jwsObject = JWSObject(JWSHeader(JWSAlgorithm.HS256), Payload(dossierJwtPayload))
            // Apply the HMAC
            jwsObject.sign(signer)
            // To serialize to compact form, produces something like
            // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
            return jwsObject.serialize()
        }

        private fun removePrefix(s: String?, prefix: String?): String? {
            return if (s != null && prefix != null && s.startsWith(prefix)) {
                s.substring(prefix.length)
            } else s
        }
    }
}