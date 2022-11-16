package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(Include.NON_NULL)
data class DossierStatus(val email: DossierEntryState?,
                         val enterprise: DossierEntryState?,
                         val consistency: DossierEntryState,
                         val questions: Map<String, DossierEntryState>?,
                         val residential_address: DossierEntryState?,
                         val id_document: DossierEntryState?,
                         val documents: Map<DossierDocument, DossierEntryState>?,
                         val id: String?,
                         val delivery: DossierEntryState,
                         val group: String,
                         val phone: DossierEntryState?,
                         val failures: Map<String, Failure>?) {

    companion object {
        fun of(dossierData: DossierData?): DossierStatus {
            return DossierStatus(
                dossierData?.email?.state,
                dossierData?.enterprise?.state,
                DossierEntryState.PENDING,
                dossierData?.questions?.map {
                                           it.question to it.state
                }?.toMap(),
                dossierData?.residentialAddress?.state,
                dossierData?.idDocument?.state,
                null,
                dossierData?.id,
                DossierEntryState.PENDING,
                "group",
                dossierData?.phone?.state,
                mapOf("phone" to Failure("check_failed", "check_failed", "Phone failure reason"), "email" to Failure("check_failed", "check_failed", "Email failure reason")))
        }
    }
}
