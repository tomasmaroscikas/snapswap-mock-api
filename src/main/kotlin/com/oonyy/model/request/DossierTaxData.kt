package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierTaxData(@param:JsonProperty("country") val country: String,
                          @param:JsonProperty("tax_id") val value: String)
