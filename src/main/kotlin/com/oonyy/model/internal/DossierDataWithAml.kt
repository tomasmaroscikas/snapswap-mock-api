package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class DossierDataWithAml(dossierData: DossierData,
    @get:JsonProperty("aml_checks") var amlCheck: Array<DossierAmlCheck>? = null
) {
    @get:JsonProperty("customer_id") var customerId: String? = dossierData.customerId
    val type: DossierType = dossierData.type
    var email: DossierEmail? = dossierData.email
    var consistency: DossierEntryState = dossierData.consistency
    var delivery: DossierEntryState = dossierData.delivery
    var enterprise: DossierCompanyInformation? = dossierData.enterprise
    var questions: MutableSet<DossierQuestionAndAnswer>? = dossierData.questions
    @get:JsonProperty("residential_address") var residentialAddress: DossierResidentialAddress? = dossierData.residentialAddress
    @get:JsonProperty("id_document") var idDocument: DossierIdDocument? = dossierData.idDocument
    var documents: MutableSet<DossierDocument> = dossierData.documents
    var id: String = dossierData.id
    var phone: DossierPhone? = dossierData.phone
    @get:JsonProperty("tax_id") var taxId: DossierTaxData? = dossierData.taxId
    @get:JsonProperty("finalize_url") var finalizeUrl: String? = dossierData.finalizeUrl
    @get:JsonProperty("failures") var failures: Map<String, Failure> = dossierData.failures
}