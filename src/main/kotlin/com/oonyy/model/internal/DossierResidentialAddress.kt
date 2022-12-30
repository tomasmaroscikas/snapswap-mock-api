package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierResidentialAddress(
    @JsonProperty("residential_address") var residentialAddress: String,
    var state: DossierEntryState = DossierEntryState.PENDING
)
