package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class DossierDocument(
    @get:JsonProperty("document_type") val documentType: DossierDocumentType,
    @JsonProperty("files") val files: List<DossierDocumentFile>? = null,
    @get:JsonProperty("color_scheme") val colorScheme: String? = null,
    val readability: String? = null,
    @get:JsonProperty("evidence_of") val evidenceOf: DossierDocumentType,
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
