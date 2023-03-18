package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
open class DossierData(
    @JsonProperty("customer_id") open var customerId: String? = null,
    open val type: DossierType = DossierType.BASIC,
    open var email: DossierEmail? = null,
    open var consistency: DossierEntryState = DossierEntryState.PENDING,
    open var delivery: DossierEntryState = DossierEntryState.PENDING,
    open var enterprise: DossierCompanyInformation? = null,
    open var questions: MutableSet<DossierQuestionAndAnswer>? = mutableSetOf(),
    @JsonProperty("residential_address") open var residentialAddress: DossierResidentialAddress? = null,
    @JsonProperty("id_document") open var idDocument: DossierIdDocument? = null,
    open var documents: MutableSet<DossierDocument> = mutableSetOf(),
    open var id: String,
    open var phone: DossierPhone? = null,
    @JsonProperty("tax_id") open var taxId: DossierTaxData? = null,
    @JsonProperty("finalize_url") open var finalizeUrl: String? = null,
    @JsonProperty("failures") open var failures: Map<String, Failure> = mutableMapOf()
)
