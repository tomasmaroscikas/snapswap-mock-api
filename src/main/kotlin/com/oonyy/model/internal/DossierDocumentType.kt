package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonValue

enum class DossierDocumentType(@JsonValue val code: String) {
    RESIDENTIAL_ADDRESS("residential_address")
}
