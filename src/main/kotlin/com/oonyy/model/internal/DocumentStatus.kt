package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty

data class DocumentStatus(@JsonProperty("residential_address") val documentType: String?)
