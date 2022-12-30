package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailData(@JsonProperty("email") val email: String, @JsonProperty("verify") val verify: String)
