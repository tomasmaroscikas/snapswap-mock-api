package com.oonyy.model.internal

import kotlinx.serialization.Serializable

@Serializable
data class DossierKey(val dossierId: String, val clientId: String)
