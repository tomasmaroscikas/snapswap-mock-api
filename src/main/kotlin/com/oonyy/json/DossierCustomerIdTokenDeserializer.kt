package com.oonyy.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.oonyy.model.portal.response.DossierCustomerIdToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DossierCustomerIdTokenDeserializer : JsonDeserializer<DossierCustomerIdToken>() {

    private val logger: Logger = LoggerFactory.getLogger(DossierCustomerIdTokenDeserializer::class.java)

    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): DossierCustomerIdToken {
        logger.debug("Text from parser: ${p?.text}")
        return objectMapper.readValue(p?.text, DossierCustomerIdToken::class.java)
    }
}