package com.oonyy.model.internal

data class DossierEmail(var email: String, var state: DossierEntryState = DossierEntryState.PENDING)
