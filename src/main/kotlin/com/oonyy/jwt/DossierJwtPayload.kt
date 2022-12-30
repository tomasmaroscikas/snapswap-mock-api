package com.oonyy.jwt

import kotlinx.serialization.Serializable

@Serializable
data class DossierJwtPayload(
    val dossierId: String,
    val clientId: String,
    val exp: Long,
    val iat: Long,
    val sub: String,
    val iss: String
)