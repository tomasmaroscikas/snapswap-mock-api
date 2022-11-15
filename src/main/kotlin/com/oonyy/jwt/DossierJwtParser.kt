package com.oonyy.jwt

import com.nimbusds.jwt.SignedJWT
import org.slf4j.LoggerFactory

class DossierJwtParser {

    companion object {
        private val logger = LoggerFactory.getLogger(DossierJwtParser::class.java)

        fun parse(jwtTokenString: String): DossierJwtPayload {
            val jwtParsed = SignedJWT.parse(removePrefix(jwtTokenString,"Bearer "))
            val payloadMap = jwtParsed.payload.toJSONObject()
            logger.debug("Payload map $payloadMap")
            return DossierJwtPayload(payloadMap["dossierId"] as String, payloadMap["clientId"] as String, payloadMap["exp"] as Long, payloadMap["iat"] as Long, payloadMap["sub"] as String, payloadMap["iss"] as String)
        }

        private fun removePrefix(s: String?, prefix: String?): String? {
            return if (s != null && prefix != null && s.startsWith(prefix)) {
                s.substring(prefix.length)
            } else s
        }
    }
}