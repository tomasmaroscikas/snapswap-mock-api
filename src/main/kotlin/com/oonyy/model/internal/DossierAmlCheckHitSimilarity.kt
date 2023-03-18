package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty

@kotlinx.serialization.Serializable
data class DossierAmlCheckHitSimilarity(
    val level: String,
    val name: String,
    @get:JsonProperty("birthdate") val birthDate: String,
    val nationality: String
)
