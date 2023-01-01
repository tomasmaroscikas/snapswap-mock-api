package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierDocument(
    @JsonProperty("document_type") val documentType: DossierDocumentType,
    @JsonProperty("files") val files: List<DossierDocumentFile>? = null,
    @JsonProperty("color_scheme") val colorScheme: String? = null,
    val readability: String? = null,
    @JsonProperty("evidence_of") val evidenceOf: String? = null,
    var state: DossierEntryState
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as DossierDocument

        if (documentType == other.documentType) return true

        return false
    }
}
