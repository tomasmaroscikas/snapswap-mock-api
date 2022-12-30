package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailConfirmationData(
    @JsonProperty("email") val email: String,
    @JsonProperty("code") val code: String
)
