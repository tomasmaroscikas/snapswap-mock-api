package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
open class DossierData(
    @JsonProperty("customer_id") open var customerId: String? = null,
    val type: DossierType = DossierType.BASIC,
    var email: DossierEmail? = null,
    var consistency: DossierEntryState = DossierEntryState.PENDING,
    var delivery: DossierEntryState = DossierEntryState.PENDING,
    var enterprise: DossierCompanyInformation? = null,
    var questions: MutableSet<DossierQuestionAndAnswer>? = mutableSetOf(),
    @JsonProperty("residential_address") open var residentialAddress: DossierResidentialAddress? = null,
    @JsonProperty("id_document") open var idDocument: DossierIdDocument? = null,
    var documents: MutableSet<DossierDocument> = mutableSetOf(),
    var id: String,
    var phone: DossierPhone? = null,
    @JsonProperty("tax_id") open var taxId: DossierTaxData? = null,
    @JsonProperty("finalize_url") open var finalizeUrl: String? = null,
    @JsonProperty("failures") open var failures: Map<String, Failure> = mutableMapOf()
)
