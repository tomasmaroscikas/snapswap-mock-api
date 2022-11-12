package com.oonyy.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Dossier(@param:JsonProperty("client_id") val clientId: String, @param:JsonProperty("openid_auth_code") val openidAuthCode: String)
