package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DossierTaxData(
    @JsonProperty("country") val country: String,
    @JsonProperty("tax_id") val value: String
)
