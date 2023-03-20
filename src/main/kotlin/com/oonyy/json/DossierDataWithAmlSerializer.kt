package com.oonyy.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.oonyy.model.internal.DossierDataWithAml


class DossierDataWithAmlSerializer {

    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

        fun serialize(dossierDataWithAml: Array<DossierDataWithAml>): String {
            return objectMapper.writeValueAsString(dossierDataWithAml)
        }
    }
}