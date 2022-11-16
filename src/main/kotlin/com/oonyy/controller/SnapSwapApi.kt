package com.oonyy.controller

import com.oonyy.jwt.DossierAuthToken
import com.oonyy.jwt.DossierJwtParser
import com.oonyy.jwt.DossierJwtPayload
import com.oonyy.jwt.DossierJwtToken
import com.oonyy.model.internal.*
import com.oonyy.response.ResponseData
import com.oonyy.model.request.*
import com.oonyy.model.stat.EndPointHitStatistics
import io.micronaut.http.HttpHeaders.AUTHORIZATION
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Controller("snapswap/mock")
class SnapSwapApi {

    private val logger: Logger = LoggerFactory.getLogger(SnapSwapApi::class.java)
    private var state: MutableMap<String, DossierData> = mutableMapOf()
    private var endPointHitStatistics = EndPointHitStatistics(0, 0, 0, 0, 0)
    var stateCount  = 0

    @Get("/api/v1/dossier")
    fun status(@Header(AUTHORIZATION) jwtTokenString: String): HttpResponse<DossierStatus> {
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierType = DossierType from dossierJwtPayload.clientId
        logger.debug("Dossier status for $dossierType")
        return if (state.containsKey(dossierJwtPayload.dossierId)) {
            HttpResponse.ok(DossierStatus.of(state[dossierJwtPayload.dossierId]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("/api/v1/dossier")
    fun createDossier(@Body dossierInitiationData: DossierInitiationData): DossierJwtToken {
        logger.debug("Bearer token request: $dossierInitiationData")
        val dossierType = DossierType from dossierInitiationData.clientId

        if (!state.containsKey(dossierInitiationData.openidAuthCode)) {
            state[dossierInitiationData.openidAuthCode] =
                DossierData(id = dossierInitiationData.openidAuthCode, type = dossierType)
        }
        val dossierJwtPayload = DossierJwtPayload(dossierInitiationData.openidAuthCode, dossierInitiationData.clientId, 1668507375000L, 1667902575000L, UUID.randomUUID().toString(), "snapswap-mock")
        // create and return JWT token
        val jwtToken = DossierJwtParser.createJwtToken(Json.encodeToString(dossierJwtPayload))
        return DossierJwtToken(DossierAuthToken(token = jwtToken))
    }

    @Get("/api/v1/dossier/data")
    fun data(): String {
        logger.debug("DossierBearerToken Data")
        return ResponseData.DOSSIER_DATA
    }

    @Get("/api/v1/dossier/id_document/allowed")
    fun allowedDocuments(): String {
        logger.debug("Received GET request to /api/v1/dossier/id_document/allowed")
        return ResponseData.ALLOWED_DOCUMENTS
    }

    @Post("/api/v1/dossier/enterprise")
    fun addEnterprise(@Header(AUTHORIZATION) jwtTokenString: String, content: EnterpriseData): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to /api/v1/dossier/enterprise: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        endPointHitStatistics.enterpriseHitCount++
        return if (state.containsKey(dossierJwtPayload.dossierId)) {
            state[dossierJwtPayload.dossierId]?.enterprise = DossierCompanyInformation(content.companyName, content.registrationNumber, content.registrationCountry, content.registrationAddress, content.businessAddress, DossierEntryState.PENDING)
            HttpResponse.ok(DossierStatus.of(state[dossierJwtPayload.dossierId]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("/api/v1/dossier/phone")
    fun addPhone(@Header(AUTHORIZATION) jwtTokenString: String, content: PhoneData): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to /api/v1/dossier/phone: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        endPointHitStatistics.phoneEndpointHitCount++
        return if (state.containsKey(dossierJwtPayload.dossierId)) {
            state[dossierJwtPayload.dossierId]?.phone = DossierPhone(content.phone, DossierEntryState.PENDING)
            HttpResponse.ok(DossierStatus.of(state[dossierJwtPayload.dossierId]))
        } else {
            HttpResponse.notFound()
        }
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
    fun addEmail(@Header(AUTHORIZATION) jwtTokenString: String, content: EmailData): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to /api/v1/dossier/email: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        endPointHitStatistics.emailEndpointHitCount++
        return if (state.containsKey(dossierJwtPayload.dossierId)) {
            state[dossierJwtPayload.dossierId]?.email = DossierEmail(content.email, DossierEntryState.PENDING)
            HttpResponse.ok(DossierStatus.of(state[dossierJwtPayload.dossierId]))
        } else {
            HttpResponse.notFound()
        }
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
    fun addResidentialAddress(@Header(AUTHORIZATION) jwtTokenString: String, content: ResidentialAddressData): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to /api/v1/dossier/residential_address: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        endPointHitStatistics.residentialAddressHitCount++
        return if (state.containsKey(dossierJwtPayload.dossierId)) {
            state[dossierJwtPayload.dossierId]?.residentialAddress = DossierResidentialAddress(content.streetAddress, DossierEntryState.PENDING)
            HttpResponse.ok(DossierStatus.of(state[dossierJwtPayload.dossierId]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("/api/v1/dossier/questions")
    fun addQuestions(@Header(AUTHORIZATION) jwtTokenString: String, @Body content: List<QuestionData>): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to /api/v1/dossier/questions: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        endPointHitStatistics.questionsHitCount++
        return if (state.containsKey(dossierJwtPayload.dossierId)) {
            content.forEach {
                state[dossierJwtPayload.dossierId]?.questions?.add(DossierQuestionAndAnswer(it.question, it.answer, DossierEntryState.PENDING))
            }
            HttpResponse.ok(DossierStatus.of(state[dossierJwtPayload.dossierId]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Get("/api/v1/dossier/list")
    fun dossierList() : List<DossierData> = state.values.toList()

    @Get("/api/v1/stats")
    fun stat(): EndPointHitStatistics {
        return endPointHitStatistics
    }

    @Post("/api/v1/dossier/status")
    fun updateStatus(statusUpdateData: StatusUpdateData): HttpStatus {
        logger.debug("Status update request $statusUpdateData")
        return if (state.containsKey(statusUpdateData.dossierId)) {
            when(statusUpdateData.type) {
                "phone" -> state[statusUpdateData.dossierId]?.phone?.state = statusUpdateData.status
                "email" -> state[statusUpdateData.dossierId]?.email?.state = statusUpdateData.status
            }
            HttpStatus.OK
        } else {
            HttpStatus.NOT_FOUND
        }
    }
}