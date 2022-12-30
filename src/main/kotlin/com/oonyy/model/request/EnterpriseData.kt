package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

data class EnterpriseData(@param:JsonProperty("company_name") val companyName: String,
                          @param:JsonProperty("registration_number") val registrationNumber: String,
                          @param:JsonProperty("country") val registrationCountry: String,
                          @param:JsonProperty("registration_address") val registrationAddress: String,
                          @param:JsonProperty("business_address") val businessAddress: String,
)