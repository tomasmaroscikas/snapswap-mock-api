package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PhoneData(@param:JsonProperty("phone") val phone: String, @param:JsonProperty("verify") val verify: String)
