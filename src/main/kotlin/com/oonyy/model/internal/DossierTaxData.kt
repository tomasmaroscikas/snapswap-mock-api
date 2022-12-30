package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierTaxData(
    @JsonProperty("country") val country: String,
    @JsonProperty("value") val value: String
)
