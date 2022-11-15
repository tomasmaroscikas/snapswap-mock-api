package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PhoneConfirmationData(@param:JsonProperty("phone") val phone: String,
                                 @param:JsonProperty("code") val verify: String)
