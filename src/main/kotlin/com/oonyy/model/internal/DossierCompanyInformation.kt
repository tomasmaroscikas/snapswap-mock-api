package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierCompanyInformation(
    @get:JsonProperty("company_name") var companyName: String,
    @get:JsonProperty("registration_number") var registrationNumber: String,
    @get:JsonProperty("country") var country: String,
    @get:JsonProperty("registration_address") var registrationAddress: String,
    @get:JsonProperty("business_address") var businessAddress: String,
    @get:JsonProperty("state") var state: DossierEntryState = DossierEntryState.PENDING
)
