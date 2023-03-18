package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty

data class DossierAmlCheckHitList(
    @get:JsonProperty("number_in_list") val numberInList: Int,
    val name: String,
    val type: String
)