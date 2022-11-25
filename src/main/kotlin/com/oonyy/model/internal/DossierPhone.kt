package com.oonyy.model.internal

import kotlinx.serialization.Serializable

@Serializable
data class DossierPhone(var phone: String, var state: DossierEntryState = DossierEntryState.PENDING)
