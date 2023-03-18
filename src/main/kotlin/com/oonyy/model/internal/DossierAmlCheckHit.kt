package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class DossierAmlCheckHit(
    @get:JsonProperty("last_update") val lastUpdate: String,
    @get:JsonProperty("is_active_in_list") val isActiveInList: Boolean,
    val reason: String,
    @get:JsonProperty("given_name") val givenName: String,
    @get:JsonProperty("family_name") val familyName: String,
    @get:JsonProperty("birth_year") val birthYear : Int,
    val nationality: String,
    val list: DossierAmlCheckHitList,
    @get:JsonProperty("number_in_list") val numberInList: Int,
    val similarity: DossierAmlCheckHitSimilarity,
)
