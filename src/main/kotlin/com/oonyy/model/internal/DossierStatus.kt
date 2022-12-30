package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
data class DossierStatus(
    val email: DossierEntryState?,
    val enterprise: DossierEntryState?,
    val consistency: DossierEntryState,
    val questions: Map<String, DossierEntryState>?,
    val residential_address: DossierEntryState?,
    val id_document: DossierEntryState?,
    val documents: DocumentStatus?,
    val id: String?,
    val delivery: DossierEntryState,
    val group: String,
    val phone: DossierEntryState?,
    val failures: Map<String, Failure>?
) {

    companion object {
        fun of(dossierData: DossierData?): DossierStatus {
            return DossierStatus(
                dossierData?.email?.state,
                dossierData?.enterprise?.state,
                dossierData!!.consistency,
                dossierData?.questions?.associate { it.question to it.state },
                dossierData?.residentialAddress?.state ?: DossierEntryState.PENDING,
                dossierData?.idDocument?.state,
                DocumentStatus(
                    dossierData?.documents?.firstOrNull { it.documentType == DossierDocumentType.RESIDENTIAL_ADDRESS }?.state?.code
                        ?: DossierEntryState.PENDING.code
                ),
                dossierData?.id,
                dossierData!!.delivery,
                "group",
                dossierData?.phone?.state,
                mapOf(
                    "phone" to Failure("check_failed", "check_failed", "Phone failure reason"),
                    "email" to Failure("check_failed", "check_failed", "Email failure reason")
                )
            )
        }
    }
}
