package com.oonyy.controller

import com.oonyy.jwt.DossierJwtParser
import com.oonyy.model.internal.*
import com.oonyy.response.ResponseData
import com.oonyy.model.request.*
import com.oonyy.model.stat.EndPointHitStatistics
import io.micronaut.http.HttpHeaders.AUTHORIZATION
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Controller("snapswap/mock")
class SnapSwapApi {

    private val logger: Logger = LoggerFactory.getLogger(SnapSwapApi::class.java)
    private var state: MutableMap<String, DossierData> = mutableMapOf()
    private var endPointHitStatistics = EndPointHitStatistics(0, 0)
    var stateCount  = 0

    @Get("/api/v1/dossier")
    fun status(@Header(AUTHORIZATION) jwtTokenString: String): DossierLimitedStatus {

        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierType = DossierType from dossierJwtPayload.clientId
        logger.debug("DossierBearerToken type from JWT token payload is $dossierType")

       /* if (state.containsKey(dossierJwtPayload.dossierId)) {

        } else {
            state[dossierJwtPayload.dossierId] = DossierData()
        }*/
        return if (dossierType == DossierType.DOSSIER_BASIC) {
            DossierLimitedStatus(
                DossierEntryState.PENDING,
                DossierEntryState.PENDING,
                DossierEntryState.PENDING,
                mapOf(DossierQuestion.REPRESENTATIVE_NATIONALITY to DossierEntryState.PENDING, DossierQuestion.REPRESENTATIVE_CAPACITY to DossierEntryState.PENDING),
                DossierEntryState.PENDING,
                DossierEntryState.PENDING,
                mapOf(DossierDocument.RESIDENTIAL_ADDRESS to DossierEntryState.PENDING),
                dossierJwtPayload.dossierId,
                DossierEntryState.PENDING,
                "group",
                DossierEntryState.PENDING,
                mapOf("phone" to Failure("check_failed", "check_failed", "Failure reason")))
        } else {
            val phoneState = if (++stateCount % 3 == 0) DossierEntryState.FAILURE else DossierEntryState.PENDING
            if (phoneState == DossierEntryState.FAILURE) stateCount = 2
            DossierLimitedStatus(
                phoneState,
                DossierEntryState.PENDING,
                DossierEntryState.PENDING,
                mapOf(DossierQuestion.REPRESENTATIVE_NATIONALITY to DossierEntryState.PENDING, DossierQuestion.REPRESENTATIVE_CAPACITY to DossierEntryState.PENDING),
                DossierEntryState.PENDING,
                DossierEntryState.PENDING,
                mapOf(DossierDocument.RESIDENTIAL_ADDRESS to DossierEntryState.PENDING),
                dossierJwtPayload.dossierId,
                DossierEntryState.PENDING,
                "group",
                phoneState,
                mapOf("phone" to Failure("check_failed", "check_failed", "Phone failure reason"), "email" to Failure("check_failed", "check_failed", "Email failure reason")))
        }
    }

    @Get("/api/v1/dossier/data")
    fun data(): String {
        logger.debug("DossierBearerToken Data")
        return ResponseData.DOSSIER_DATA
    }

    @Post("/api/v1/dossier")
    fun createDossier(@Body content: DossierInitiationData): String {
        logger.debug("Bearer token request: $content")
        return if (DossierType from content.clientId == DossierType.DOSSIER_BASIC) {
            ResponseData.BEARER_TOKEN_BASIC
        } else {
            ResponseData.BEARER_TOKEN_LIMITED
        }
    }

    @Get("/api/v1/dossier/id_document/allowed")
    fun allowedDocuments(): String {
        logger.debug("Received GET request to /api/v1/dossier/id_document/allowed")
        return ResponseData.ALLOWED_DOCUMENTS
    }

    @Post("/api/v1/dossier/enterprise")
    fun addEnterprise(content: EnterpriseData): String {
        logger.debug("Received POST request to /api/v1/dossier/enterprise: $content")
        return ResponseData.DOSSIER_STATUS_BASIC
    }

    @Post("/api/v1/dossier/phone")
    fun addPhone(content: PhoneData): String {
        logger.debug("Received POST request to /api/v1/dossier/phone: $content")
        endPointHitStatistics.phoneEndpointHitCount++
        return ResponseData.DOSSIER_STATUS_BASIC
    }

    @Post("/api/v1/dossier/phone/confirmation")
    fun confirmPhone(content: PhoneConfirmationData): String {
        logger.debug("Received POST request to /api/v1/dossier/phone/confirmation: $content")
        return """{
                    "type": "https://rkyc.snapswap.vc/api/errors/access-denied",
                    "title": "Access denied",
                    "status": 403,
                    "detail": "The request was a legal request, but the server is refusing to respond to it."
                }"""
    }

    @Post("/api/v1/dossier/email")
    fun addEmail(content: EmailData): String {
        logger.debug("Received POST request to /api/v1/dossier/email: $content")
        endPointHitStatistics.emailEndpointHitCount++
        return ResponseData.DOSSIER_STATUS_BASIC
    }
    @Post("/api/v1/dossier/email/confirmation")
    fun confirmEmail(content: EmailConfirmationData): String {
        logger.debug("Received POST request to /api/v1/dossier/email/confirmation: $content")
        return """{
                    "type": "https://rkyc.snapswap.vc/api/errors/access-denied",
                    "title": "Access denied",
                    "status": 403,
                    "detail": "The request was a legal request, but the server is refusing to respond to it."
                }"""
    }

    @Post("/api/v1/dossier/residential_address")
    fun addResidentialAddress(content: ResidentialAddressData): String {
        logger.debug("Received POST request to /api/v1/dossier/residential_address: $content")
        return ResponseData.DOSSIER_STATUS_BASIC
    }

    @Post("/api/v1/dossier/questions")
    fun addQuestions(@Body content: List<QuestionData>): String {
        logger.debug("Received POST request to /api/v1/dossier/questions: $content")
        return ResponseData.DOSSIER_STATUS_BASIC
    }

    @Get("/api/v1/stats")
    fun stat(): EndPointHitStatistics {
        return endPointHitStatistics
    }
}