package com.oonyy.model.internal

import kotlinx.serialization.Serializable

@Serializable
data class DossierResidentialAddress(var residentialAddress: String, var state: DossierEntryState = DossierEntryState.PENDING)
