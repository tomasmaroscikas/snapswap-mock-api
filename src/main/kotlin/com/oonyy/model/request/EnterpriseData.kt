package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EnterpriseData(@param:JsonProperty("companyName") val companyName: String,
                          @param:JsonProperty("registrationNumber") val registrationNumber: String,
                          @param:JsonProperty("registrationCountry") val registrationCountry: String,
                          @param:JsonProperty("registrationAddress") val registrationAddress: String,
                          @param:JsonProperty("businessAddress") val businessAddress: String,
)