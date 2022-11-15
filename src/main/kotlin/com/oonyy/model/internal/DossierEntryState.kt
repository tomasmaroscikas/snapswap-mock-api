package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonValue

enum class DossierEntryState(@JsonValue val code: String) {
    PENDING("pending"),
    CONFIRMING("confirming"),
    SUCCESS("success"),
    FAILURE("failure")
}