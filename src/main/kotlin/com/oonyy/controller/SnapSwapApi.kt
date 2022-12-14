package com.oonyy.controller

import com.oonyy.client.PortalClient
import com.oonyy.controller.SnapSwapApi.Companion.API_PREFIX
import com.oonyy.jwt.DossierAuthToken
import com.oonyy.jwt.DossierJwtParser
import com.oonyy.jwt.DossierJwtPayload
import com.oonyy.jwt.DossierJwtToken
import com.oonyy.model.internal.*
import com.oonyy.model.portal.SnapSwapEnvironment
import com.oonyy.model.request.*
import com.oonyy.model.request.DossierTaxData
import com.oonyy.model.stat.EndPointHitStatistics
import com.oonyy.openid.TokenRequest
import com.oonyy.response.RepresentativeIdDocumentProcess
import com.oonyy.response.ResponseData
import com.oonyy.service.PersistenceService
import io.micronaut.http.HttpHeaders.AUTHORIZATION
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.MediaType.MULTIPART_FORM_DATA
import io.micronaut.http.annotation.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.util.*

@Controller("$API_PREFIX")
class SnapSwapApi(private val portalClient: PortalClient, private val persistenceService: PersistenceService) {

    private val logger: Logger = LoggerFactory.getLogger(SnapSwapApi::class.java)
    private var state: MutableMap<DossierKey, DossierData> = mutableMapOf()
    private var endPointHitStatistics = EndPointHitStatistics()
    private val tokens: MutableMap<String, String> = mutableMapOf()

    @Get("$ENDPOINT_PREFIX")
    fun status(@Header(AUTHORIZATION) jwtTokenString: String): HttpResponse<DossierStatus> {
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierType = DossierType from dossierJwtPayload.clientId
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        logger.debug("Dossier status for $dossierType")
        return if (state.containsKey(dossierKey)) {
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("$ENDPOINT_PREFIX")
    fun createDossier(@Body dossierInitiationData: DossierInitiationData): DossierJwtToken {
        logger.debug("Bearer token request: $dossierInitiationData")
        val dossierType = (DossierType from dossierInitiationData.clientId) ?: DossierType.DOSSIER_LIMITED
        val dossierKey = DossierKey(dossierInitiationData.openidAuthCode, dossierInitiationData.clientId)
        if (!state.containsKey(dossierKey)) {
            // query Portal to get CustomerId
            // TODO handle when portal is not available
            val idToken = portalClient.getCustomerId(
                SnapSwapEnvironment.DEVELOPMENT,
                "authorization_code",
                dossierInitiationData.openidAuthCode
            )
            state[dossierKey] =
                DossierData(
                    id = dossierInitiationData.openidAuthCode,
                    type = dossierType,
                    customerId = idToken.idToken.sub,
                    idDocument = DossierIdDocument(1234567890, DossierEntryState.PENDING)
                )
        }
        val dossierJwtPayload = DossierJwtPayload(
            dossierInitiationData.openidAuthCode,
            dossierInitiationData.clientId,
            1668507375000L,
            1667902575000L,
            UUID.randomUUID().toString(),
            "snapswap-mock"
        )
        // create and return JWT token
        val jwtToken = DossierJwtParser.createJwtToken(Json.encodeToString(dossierJwtPayload))
        return DossierJwtToken(DossierAuthToken(token = jwtToken))
    }

    @Post("/openid/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun getCustomerId(@Body content: TokenRequest): String? {
        return if (tokens.containsKey(content.code)) {
            tokens[content.code]
        } else {
            val customerId = createCustomerId(content.code)
            tokens[content.code] = customerId;
            endPointHitStatistics.customerCount++
            customerId;
        }
    }

    @Get("/openid/list")
    fun listCustomerIds() = tokens

    private fun createCustomerId(code: String): String {
        val currentDate = LocalDate.now()
        return """
            {"id_token": "{\"aud\":\"snapswap-rkyc\",\"sub\":\"ORGANIZATION_${tokens.size}_${currentDate}\",\"iss\":\"https://www.customweb.com\"}"}
        """.trim()
    }

    @Get("$ENDPOINT_PREFIX/data")
    fun data(@Header(AUTHORIZATION) jwtTokenString: String): HttpResponse<DossierData> {
        logger.debug("DossierBearerToken Data")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        return if (state.containsKey(dossierKey)) {
            HttpResponse.ok(state[dossierKey])
        } else {
            HttpResponse.notFound()
        }
    }

    @Get("$ENDPOINT_PREFIX/id_document/allowed")
    fun allowedDocuments(): String {
        logger.debug("Received GET request to $ENDPOINT_PREFIX/id_document/allowed")
        return ResponseData.ALLOWED_DOCUMENTS
    }

    @Post("$ENDPOINT_PREFIX/enterprise")
    fun addEnterprise(
        @Header(AUTHORIZATION) jwtTokenString: String,
        @Body content: EnterpriseData
    ): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/enterprise: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.enterpriseHitCount++
        return if (state.containsKey(dossierKey)) {
            state[dossierKey]?.enterprise = DossierCompanyInformation(
                content.companyName,
                content.registrationNumber,
                content.country,
                content.registrationAddress,
                content.businessAddress,
                DossierEntryState.PENDING
            )
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("$ENDPOINT_PREFIX/tax_id")
    fun addTax(
        @Header(AUTHORIZATION) jwtTokenString: String,
        @Body content: DossierTaxData
    ): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/tax: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.taxCount++
        return if (state.containsKey(dossierKey)) {
            // Here we have two classes with the same name, because of different property names in request and how SnapSwap responds later
            state[dossierKey]?.taxId = com.oonyy.model.internal.DossierTaxData(content.country, content.value)
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("$ENDPOINT_PREFIX/phone")
    fun addPhone(@Header(AUTHORIZATION) jwtTokenString: String, content: PhoneData): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/phone: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.phoneEndpointHitCount++
        return if (state.containsKey(dossierKey)) {
            state[dossierKey]?.phone = DossierPhone(content.phone, DossierEntryState.PENDING)
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("$ENDPOINT_PREFIX/phone/confirmation")
    fun confirmPhone(content: PhoneConfirmationData): String {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/phone/confirmation: $content")
        return """{
                    "type": "https://rkyc.snapswap.vc/api/errors/access-denied",
                    "title": "Access denied",
                    "status": 403,
                    "detail": "The request was a legal request, but the server is refusing to respond to it."
                }"""
    }

    @Post("$ENDPOINT_PREFIX/email")
    fun addEmail(@Header(AUTHORIZATION) jwtTokenString: String, content: EmailData): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/email: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.emailEndpointHitCount++
        return if (state.containsKey(dossierKey)) {
            state[dossierKey]?.email = DossierEmail(content.email, DossierEntryState.PENDING)
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("$ENDPOINT_PREFIX/email/confirmation")
    fun confirmEmail(content: EmailConfirmationData): String {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/email/confirmation: $content")
        return """{
                    "type": "https://rkyc.snapswap.vc/api/errors/access-denied",
                    "title": "Access denied",
                    "status": 403,
                    "detail": "The request was a legal request, but the server is refusing to respond to it."
                }"""
    }

    @Post("$ENDPOINT_PREFIX/residential_address")
    fun addResidentialAddress(
        @Header(AUTHORIZATION) jwtTokenString: String,
        content: ResidentialAddressData
    ): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/residential_address: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.amlRequestCount++
        return if (state.containsKey(dossierKey)) {
            // add to dedicated entity
            state[dossierKey]?.residentialAddress =
                DossierResidentialAddress(content.streetAddress, DossierEntryState.PENDING)
            // add entry to document
            state[dossierKey]?.documents?.add(
                DossierDocument(
                    documentType = DossierDocumentType.RESIDENTIAL_ADDRESS,
                    state = DossierEntryState.PENDING
                )
            )
            logger.warn("This shouldn't be here 1")
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("$ENDPOINT_PREFIX/aml_check/person/questions/{questionId}")
    fun triggerUboAml(
        @Header(AUTHORIZATION) jwtTokenString: String,
        @PathVariable questionId: String,
        @Body content: DossierAmlPerson
    ): HttpResponse<DossierStatus> {
        logger.debug("$ENDPOINT_PREFIX/aml_check/person/questions/$questionId: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.amlRequestCount++
        return if (state.containsKey(dossierKey)) {
            // It's ok to do nothing with received data, at least for now
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post("$ENDPOINT_PREFIX/questions")
    fun addQuestions(
        @Header(AUTHORIZATION) jwtTokenString: String,
        @Body content: List<QuestionData>
    ): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/questions: $content")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.questionsHitCount++
        return if (state.containsKey(dossierKey)) {
            content.forEach {
                state[dossierKey]?.questions?.add(
                    DossierQuestionAndAnswer(
                        it.question,
                        it.answer,
                        DossierEntryState.PENDING
                    )
                )

                if (it.question.contains("doc_number")) {
                    state[dossierKey]?.documents?.add(
                        DossierDocument(
                            documentType = DossierDocumentType.QUESTIONS,
                            state = DossierEntryState.PENDING
                        )
                    )
                }
            }
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Post(value = "$ENDPOINT_PREFIX/documents/questions/{documentType}/{questionId}", consumes = [MULTIPART_FORM_DATA])
    fun addDocument(
        @Header(AUTHORIZATION) jwtTokenString: String,
        @PathVariable documentType: String,
        @PathVariable questionId: String,
        @Body file: ByteArray
    ): HttpResponse<DossierStatus> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/documents/questions/$documentType/$questionId")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        endPointHitStatistics.documentsHitCount++
        return if (state.containsKey(dossierKey)) {
            // TODO not sure if we call this endpoint
            state[dossierKey]?.documents?.add(
                DossierDocument(
                    documentType = DossierDocumentType.RESIDENTIAL_ADDRESS,
                    state = DossierEntryState.PENDING
                )
            )
            logger.warn("This shouldn't be here 2")
            HttpResponse.ok(DossierStatus.of(state[dossierKey]))
        } else {
            HttpResponse.notFound()
        }
    }

    @Delete("$ENDPOINT_PREFIX/id_document/jumio/latest")
    fun cancelRepresentativeDocument(@Header(AUTHORIZATION) jwtTokenString: String): HttpResponse<Map<String, String>> {
        logger.debug("Received DELETE request to $ENDPOINT_PREFIX/id_document/jumio/latest")
        endPointHitStatistics.cancelRepresentativeIdDocumentCount++
        return HttpResponse.ok(mapOf("fake" to "structure"))
    }

    @Post("$ENDPOINT_PREFIX/id_document/jumio/web4")
    fun initRepresentativeIdDocumentProcess(@Header(AUTHORIZATION) jwtTokenString: String, data: Map<String, String>): HttpResponse<RepresentativeIdDocumentProcess> {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/id_document/jumio/web4")
        endPointHitStatistics.initRepresentativeIdDocumentVerificationCount++
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        return if (state.containsKey(dossierKey)) {
            state[dossierKey]?.finalizeUrl = data["redirect_url"]
            HttpResponse.ok(RepresentativeIdDocumentProcess("https://localhost:8443/$ID_DOCUMENT_FINALIZE", "jumioScanRefrenceFake", "-1"))
        } else {
            HttpResponse.notFound()
        }
    }

    @Get("$ID_DOCUMENT_FINALIZE")
    @Produces(MediaType.TEXT_PLAIN)
    fun finalizeRepresentativeIdDocumentProcess(): HttpResponse<String> = HttpResponse.ok("You can trigger finalize upload using Postman")

    @Post("$ENDPOINT_PREFIX/delivery")
    fun dossierDeliver(@Header(AUTHORIZATION) jwtTokenString: String): HttpStatus {
        endPointHitStatistics.deliveryCount++
        return HttpStatus.OK
    }

    /**
     * Helper to have possibility to add failures
     */
    @Post("$ENDPOINT_PREFIX/failure")
    fun add(@Header(AUTHORIZATION) jwtTokenString: String, data: Map<String, Failure>): HttpStatus {
        logger.debug("Received POST request to $ENDPOINT_PREFIX/failure")
        endPointHitStatistics.addFailureCount++
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        return if (state.containsKey(dossierKey)) {
            state[dossierKey]?.failures = data
            HttpStatus.OK
        } else {
            HttpStatus.NOT_FOUND
        }
    }

    @Get("$ENDPOINT_PREFIX/list")
    fun dossierList(): List<DossierData> = state.values.toList()

    @Get("/api/v1/stats")
    fun stat(): EndPointHitStatistics {
        return endPointHitStatistics
    }

    @Post("$ENDPOINT_PREFIX/status")
    fun updateStatus(@Header(AUTHORIZATION) jwtTokenString: String, statusUpdateData: StatusUpdateData): HttpStatus {
        logger.debug("Status update request $statusUpdateData")
        val dossierJwtPayload = DossierJwtParser.parse(jwtTokenString)
        val dossierKey = DossierKey(dossierJwtPayload.dossierId, dossierJwtPayload.clientId)
        return if (state.containsKey(dossierKey)) {
            when (statusUpdateData.type) {
                "phone" -> state[dossierKey]?.phone?.state = statusUpdateData.status
                "email" -> state[dossierKey]?.email?.state = statusUpdateData.status
                "residential_address" -> state[dossierKey]?.residentialAddress?.state = statusUpdateData.status
                "enterprise" -> state[dossierKey]?.enterprise?.state = statusUpdateData.status
                "consistency" -> state[dossierKey]?.consistency = statusUpdateData.status
                "delivery" -> state[dossierKey]?.delivery = statusUpdateData.status
                "id_document" -> state[dossierKey]?.idDocument?.state = statusUpdateData.status
                    "residential_address_document" -> state[dossierKey]?.documents?.filter { it.documentType == DossierDocumentType.RESIDENTIAL_ADDRESS }
                    ?.forEach { it.state = statusUpdateData.status }
                "document_questions" -> state[dossierKey]?.documents?.filter { it.documentType == DossierDocumentType.QUESTIONS }
                    ?.forEach { it.state = statusUpdateData.status }
                "questions" -> state[dossierKey]?.questions?.forEach { it.state = statusUpdateData.status }
            }
            HttpStatus.OK
        } else {
            HttpStatus.NOT_FOUND
        }
    }

    @Get("$ENDPOINT_PREFIX/state/reset")
    fun resetState(): HttpStatus {
        state = mutableMapOf()
        return HttpStatus.OK
    }

    @Get("$ENDPOINT_PREFIX/state/persist")
    fun saveState(): HttpStatus {
        persistenceService.writeState(state.values)
        return HttpStatus.OK
    }

    @Get("$ENDPOINT_PREFIX/state/restore")
    fun restoreState(): HttpStatus {
        state = persistenceService.readState()
        return HttpStatus.OK
    }

    companion object {
        const val API_PREFIX = "snapswap/mock"
        private const val API_VERSION = "v1"
        private const val ENDPOINT_PREFIX = "/api/$API_VERSION/dossier"
        private const val ID_DOCUMENT_FINALIZE = "$API_PREFIX$ENDPOINT_PREFIX/id_document/finalize"
    }
}