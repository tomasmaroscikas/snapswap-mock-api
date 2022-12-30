package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ResidentialAddressData(
    @param:JsonProperty("country") val country: String,
    @param:JsonProperty("region") val region: String,
    @param:JsonProperty("city") val city: String,
    @param:JsonProperty("postal_code") val postalCode: String,
    @param:JsonProperty("street_address") val streetAddress: String,
)
