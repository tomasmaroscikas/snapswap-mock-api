package com.oonyy.model

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailData(@param:JsonProperty("email") val email: String, @param:JsonProperty("verify") val verify: String)
