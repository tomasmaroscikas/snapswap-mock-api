package com.oonyy.model.internal

import kotlinx.serialization.Serializable

@Serializable
data class DossierQuestionAndAnswer(
    var question: String,
    var answer: String,
    var state: DossierEntryState
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as DossierQuestionAndAnswer

        if (question == other.question && answer == other.answer) return true

        return false
    }
}
