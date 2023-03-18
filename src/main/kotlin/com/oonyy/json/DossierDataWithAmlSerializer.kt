package com.oonyy.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.oonyy.model.internal.DossierDataWithAml

class DossierDataWithAmlSerializer {

    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()

        fun serialize(dossierDataWithAml: Array<DossierDataWithAml>): String {
            return objectMapper.writeValueAsString(dossierDataWithAml)
        }
    }
}