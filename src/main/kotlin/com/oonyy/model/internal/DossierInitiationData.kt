package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty

data class DossierInitiationData(
    @JsonProperty("client_id") val clientId: String,
    @JsonProperty("openid_auth_code") val openidAuthCode: String
)

