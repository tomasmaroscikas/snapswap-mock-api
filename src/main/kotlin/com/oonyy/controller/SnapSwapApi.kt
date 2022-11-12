package com.oonyy.controller

import com.oonyy.response.ResponseData
import com.oonyy.model.*
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory

@Controller("snapswap/mock")
class SnapSwapApi {

    private val logger = LoggerFactory.getLogger(SnapSwapApi::class.java)
    private var stateCount  = 0

    @Get("/api/v1/dossier")
    fun status(): String {
        logger.debug("Dossier Status")
        if (stateCount++ %4 == 0) return ResponseData.DOSSIER_STATUS_PHONE("failure")
        return ResponseData.DOSSIER_STATUS
    }

    @Get("/api/v1/dossier/data")
    fun data(): String {
        logger.debug("Dossier Data")
        return ResponseData.DOSSIER_DATA
    }

    @Post("/api/v1/dossier")
    fun createDossier(@Body content: Dossier): String {
        logger.debug("Bearer token request: $content")
        return ResponseData.BEARER_TOKEN
    }

    @Get("/api/v1/dossier/id_document/allowed")
    fun allowedDocuments(): String {
        logger.debug("Received GET request to /api/v1/dossier/id_document/allowed")
        return ResponseData.ALLOWED_DOCUMENTS
    }

    @Post("/api/v1/dossier/enterprise")
    fun addPhone(content: EnterpriseData): String {
        logger.debug("Received POST request to /api/v1/dossier/enterprise: $content")
        return ResponseData.DOSSIER_STATUS
    }

    @Post("/api/v1/dossier/phone")
    fun addPhone(content: PhoneData): String {
        logger.debug("Received POST request to /api/v1/dossier/phone: $content")
        return ResponseData.DOSSIER_STATUS
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
        return ResponseData.DOSSIER_STATUS
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
        return ResponseData.DOSSIER_STATUS
    }

    @Post("/api/v1/dossier/questions")
    fun addQuestions(@Body content: List<QuestionData>): String {
        logger.debug("Received POST request to /api/v1/dossier/questions: $content")
        return ResponseData.DOSSIER_STATUS
    }
}