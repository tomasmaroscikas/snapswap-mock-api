package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty

data class DossierAmlPerson(@JsonProperty("given_name") val givenName: String,
                            @JsonProperty("family_name") val familyName: String,
                            @JsonProperty("birthdate") val birthdate: String,
                            @JsonProperty("nationality") val nationality: String,)
