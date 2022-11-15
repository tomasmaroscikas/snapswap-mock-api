package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonValue

enum class DossierQuestion(@JsonValue val code: String) {
    REPRESENTATIVE_NATIONALITY("representative_nationality"),
    REPRESENTATIVE_CAPACITY("representative_capacity"),
}