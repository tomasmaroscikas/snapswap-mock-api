package com.oonyy.client

import com.oonyy.model.internal.DossierType
import com.oonyy.model.portal.SnapSwapEnvironment
import com.oonyy.model.portal.response.IdToken
import io.micronaut.http.HttpHeaders.AUTHORIZATION
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8000")
interface PortalClient {

    @Get("/payment/facilitator/snapswap/token/{environment}")
    fun getCustomerId(
        @PathVariable environment: SnapSwapEnvironment,
        @PathVariable("grant_type") grantType: String,
        @PathVariable code: String
    ): IdToken

    @Post(
        value = "/payment/facilitator/snapswap/dossier-delivery/{environment}/{dossierType}",
        consumes = ["application/vnd.snapswap.rkyc-encrypted"]
    )
    fun sendDossierDelivery(
        @Header(AUTHORIZATION) authorizationAESECB: String,
        @PathVariable environment: SnapSwapEnvironment,
        @PathVariable dossierType: DossierType,
        @Body encryptedDossier: ByteArray
    ): HttpStatus
}