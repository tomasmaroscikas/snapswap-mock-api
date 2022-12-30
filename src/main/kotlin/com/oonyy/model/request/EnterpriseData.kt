package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EnterpriseData(
    @JsonProperty("company_name") val companyName: String,
    @JsonProperty("registration_number") val registrationNumber: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("registration_address") val registrationAddress: String,
    @JsonProperty("business_address") val businessAddress: String,
)