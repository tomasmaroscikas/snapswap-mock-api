package com.oonyy.model.portal.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.oonyy.json.DossierCustomerIdTokenDeserializer

/**
 * Data which Portal responds from the Customer Id token endpoint
 *
 * <code>
{
"id_token": "{\"aud\":\"snapswap-rkyc\",\"iss\":\"https://www.customweb.com\",\"sub\":\"ORGANIZATION_1_10061765\"}"
}
 * </code>
 */
data class IdToken(@JsonProperty("id_token") @JsonDeserialize(using = DossierCustomerIdTokenDeserializer::class) var idToken: DossierCustomerIdToken)
