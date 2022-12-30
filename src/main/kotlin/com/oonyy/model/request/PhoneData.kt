package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PhoneData(@JsonProperty("phone") val phone: String, @JsonProperty("verify") val verify: String)
