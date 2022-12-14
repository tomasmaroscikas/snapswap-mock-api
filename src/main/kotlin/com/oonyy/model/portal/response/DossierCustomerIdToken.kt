package com.oonyy.model.portal.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * SnapSwap requests Portal endpoint payment/facilitator/snapswap/token/{environment}?grant_type=authorization_code&code={token_created_by_portal} to get customer Id
 *
 * Add default values so that Kotlin generate additional parameterless constructor, see https://kotlinlang.org/docs/classes.html#creating-instances-of-classes
 */
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class DossierCustomerIdToken(
    @JsonProperty var iss: String = "",
    @JsonProperty var sub: String = "",
    @JsonProperty var aud: String = ""
)
