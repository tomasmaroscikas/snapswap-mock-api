package com.oonyy.model.portal.response

import com.fasterxml.jackson.annotation.JsonProperty

data class IdToken(@JsonProperty("id_token") var idToken: DossierCustomerIdToken)
