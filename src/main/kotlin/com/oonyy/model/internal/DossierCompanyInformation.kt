package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierCompanyInformation(
    @JsonProperty("company_name") var companyName: String,
    @JsonProperty("registration_number") var registrationNumber: String,
    @JsonProperty("country") var country: String,
    @JsonProperty("registration_address") var registrationAddress: String,
    @JsonProperty("business_address") var businessAddress: String,
    @JsonProperty("state") var state: DossierEntryState = DossierEntryState.PENDING
)
