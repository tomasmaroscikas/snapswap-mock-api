package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty

data class DossierInitiationData(@param:JsonProperty("client_id") val clientId: String, @param:JsonProperty("openid_auth_code") val openidAuthCode: String)

