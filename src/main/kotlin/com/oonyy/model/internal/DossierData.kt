package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierData(
    @JsonProperty("customer_id") var customerId: String? = null,
    val type: DossierType = DossierType.DOSSIER_BASIC,
    var email: DossierEmail? = null,
    var consistency: DossierEntryState = DossierEntryState.PENDING,
    var delivery: DossierEntryState = DossierEntryState.PENDING,
    var enterprise: DossierCompanyInformation? = null,
    var questions: MutableSet<DossierQuestionAndAnswer>? = mutableSetOf(),
    @JsonProperty("residential_address") var residentialAddress: DossierResidentialAddress? = null,
    @JsonProperty("id_document") var idDocument: DossierIdDocument? = null,
    var documents: MutableSet<DossierDocument> = mutableSetOf(),
    var id: String,
    var phone: DossierPhone? = null,
    @JsonProperty("tax_id") var taxId: DossierTaxData? = null,
    @JsonProperty("finalize_url")var finalizeUrl: String? = null
)
