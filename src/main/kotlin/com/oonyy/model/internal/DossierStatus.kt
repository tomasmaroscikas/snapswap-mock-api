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
    val documents: Map<String, String>,
    val id: String?,
    val delivery: DossierEntryState,
    val group: String,
    val phone: DossierEntryState?,
    val tax_id: DossierEntryState?,
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
                dossierData?.documents.associate {  it.evidenceOf.code to it.state.code },
                dossierData?.id,
                dossierData!!.delivery,
                "group",
                dossierData?.phone?.state,
                dossierData?.taxId?.state,
                dossierData.failures
            )
        }
    }
}
