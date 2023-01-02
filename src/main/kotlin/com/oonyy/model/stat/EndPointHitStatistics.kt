package com.oonyy.model.stat

data class EndPointHitStatistics(
    var phoneEndpointHitCount: Int = 0,
    var emailEndpointHitCount: Int = 0,
    var enterpriseHitCount: Int = 0,
    var residentialAddressHitCount: Int = 0,
    var questionsHitCount: Int = 0,
    var documentsHitCount: Int = 0,
    var amlRequestCount: Int = 0,
    var customerCount: Int = 0,
    var taxCount: Int = 0,
    var delivery: Int = 0
)
