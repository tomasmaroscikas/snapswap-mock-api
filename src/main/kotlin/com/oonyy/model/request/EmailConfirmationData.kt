package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailConfirmationData(@param:JsonProperty("email") val email: String,
                                 @param:JsonProperty("code") val code: String)
