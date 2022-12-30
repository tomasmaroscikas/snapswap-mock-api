package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PhoneConfirmationData(
    @JsonProperty("phone") val phone: String,
    @JsonProperty("code") val verify: String
)
