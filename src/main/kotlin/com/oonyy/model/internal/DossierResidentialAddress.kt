package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import com.oonyy.model.request.ResidentialAddressData
import kotlinx.serialization.Serializable

@Serializable
data class DossierResidentialAddress(
    @JsonProperty("country") var country: String,
    @JsonProperty("region") var region: String,
    @JsonProperty("city") var city: String,
    @get:JsonProperty("postal_code") var postalCode: String,
    @get:JsonProperty("street_address") var streetAddress: String,
    var state: DossierEntryState = DossierEntryState.PENDING
)
