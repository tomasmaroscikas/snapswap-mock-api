package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonProperty

data class RepresentativeIdDocumentProcess(@JsonProperty("redirect_url") val redirectUrl: String,
                                           @JsonProperty("jumio_id_scan_reference") val jumioScanReference: String,
                                           @JsonProperty("attempt_id") val attemptId: String)
