package com.oonyy.model.internal

import kotlinx.serialization.Serializable

@Serializable
data class DossierDocumentFile(
    val classifier: String,
    val url: String,
    val mediaType: String,
    val encryptedAes: String
)
