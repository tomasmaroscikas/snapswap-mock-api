package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import com.oonyy.model.request.ResidentialAddressData
import kotlinx.serialization.Serializable

@Serializable
data class DossierResidentialAddress(
    @param:JsonProperty("country") var country: String,
    @param:JsonProperty("region") var region: String,
    @param:JsonProperty("city") var city: String,
    @param:JsonProperty("postal_code") var postalCode: String,
    @param:JsonProperty("street_address") var streetAddress: String,
    var state: DossierEntryState = DossierEntryState.PENDING
)
