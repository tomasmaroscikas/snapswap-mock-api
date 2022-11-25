package com.oonyy.model.internal

import kotlinx.serialization.Serializable

@Serializable
data class DossierEmail(var email: String, var state: DossierEntryState = DossierEntryState.PENDING)
