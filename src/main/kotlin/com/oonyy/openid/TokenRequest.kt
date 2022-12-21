package com.oonyy.openid

import io.micronaut.core.annotation.Introspected

@Introspected
data class TokenRequest(val grant_type: String, val code: String)
