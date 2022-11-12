package com.oonyy.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PhoneData(@param:JsonProperty("phone") val phone: String, @param:JsonProperty("verify") val verify: String)
