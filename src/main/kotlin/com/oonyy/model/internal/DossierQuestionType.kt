package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonValue
import com.oonyy.until.EnumFinder

enum class DossierQuestionType(@JsonValue val code: String) {
    REPRESENTATIVE_NATIONALITY("representative_nationality"),
    REPRESENTATIVE_CAPACITY("representative_capacity");

    companion object : EnumFinder<String, DossierType>(DossierType.values().associateBy { it.code })
}