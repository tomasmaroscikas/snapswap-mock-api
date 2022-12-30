package com.oonyy.client

import com.oonyy.model.portal.SnapSwapEnvironment
import com.oonyy.model.portal.response.IdToken
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8000")
interface PortalClient {

    @Get("/payment/facilitator/snapswap/token/{environment}")
    fun getCustomerId(
        @PathVariable environment: SnapSwapEnvironment,
        @PathVariable("grant_type") grantType: String,
        @PathVariable code: String
    ): IdToken
}