package com.oonyy.model.internal

data class DossierData(var email: DossierEmail,
                       var enterprise: DossierCompanyInformation,
                       var questions: List<DossierQuestion>,
                       var residentialAddress: DossierResidentialAddress,
                       var idDocument: DossierIdDocument,
                       var documents: List<DossierDocument>,
                       var id: String,
                       var phone: String)
