package com.oonyy.model.internal

data class DossierData(var type: DossierType? = DossierType.DOSSIER_BASIC,
                       var email: DossierEmail? = null,
                       var enterprise: DossierCompanyInformation? = null,
                       var questions: MutableList<DossierQuestionAndAnswer>? = mutableListOf(),
                       var residentialAddress: DossierResidentialAddress? = null,
                       var idDocument: DossierIdDocument? = null,
                       var documents: List<DossierDocument>? = null,
                       var id: String,
                       var phone: DossierPhone? = null)
