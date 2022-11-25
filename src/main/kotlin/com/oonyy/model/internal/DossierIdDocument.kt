package com.oonyy.model.internal

import kotlinx.serialization.Serializable

@Serializable
data class DossierIdDocument(var number: Int, var state: DossierEntryState = DossierEntryState.PENDING)
