package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class DossierAmlCheck(
    val provider: String,
    @get:JsonProperty("family_name") val familyName: String,
    @get:JsonProperty("birthdate") val birthDate: Date,
    val id: String,
    val hits: List<DossierAmlCheckHit>,
    @get:JsonProperty("country_of_residence") val countryOfResidence: String,
    @get:JsonProperty("given_name") val givenName: String,
    val gender: String,
    @get:JsonProperty("reference_entry") val referenceEntry: String,
    @get:JsonProperty("reference_entry_id") val referenceEntryId: String?,
)
