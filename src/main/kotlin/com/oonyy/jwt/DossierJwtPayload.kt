package com.oonyy.jwt

data class DossierJwtPayload(val dossierId: String, val clientId: String, val exp: Long, val iat: Long, val sub: String, val iss: String)