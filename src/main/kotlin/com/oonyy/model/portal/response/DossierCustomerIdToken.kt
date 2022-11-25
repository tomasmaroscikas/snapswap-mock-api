package com.oonyy.model.portal.response

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

/**
 * SnapSwap requests Portal endpoint payment/facilitator/snapswap/token/{environment}?grant_type=authorization_code&code={token_aka_dossier_id} to get customer Id
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class DossierCustomerIdToken(@JsonProperty var iss: String, @JsonProperty var sub: String, @JsonProperty var aud: String)
