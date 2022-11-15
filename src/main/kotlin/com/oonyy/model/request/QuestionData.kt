package com.oonyy.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class QuestionData(@param:JsonProperty("question") val question: String,
                        @param:JsonProperty("answer") val answer: String,)