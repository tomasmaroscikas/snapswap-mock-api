package com.oonyy.model.internal

data class DossierCompanyInformation(var companyName: String, var registrationNumber: String, val registrationCountry: String, var registrationAddress: String, var businessAddress: String, var state: DossierEntryState = DossierEntryState.PENDING)
