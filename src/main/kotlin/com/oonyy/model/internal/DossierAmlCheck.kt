package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class DossierAmlCheck(
    val provider: String? = null,
    @get:JsonProperty("family_name") val familyName: String? = null,
    @get:JsonProperty("birthdate") val birthDate: String? = null,
    val id: String? = null,
    val hits: List<DossierAmlCheckHit>,
    @get:JsonProperty("country_of_residence") val countryOfResidence: String? = null,
    @get:JsonProperty("given_name") val givenName: String? = null,
    @get:JsonProperty("name") val name: String? = null,
    val gender: String? = null,
    @get:JsonProperty("reference_entry") val referenceEntry: String,
    @get:JsonProperty("reference_entry_id") val referenceEntryId: String? = null,
)
