package com.oonyy.model.internal

import com.fasterxml.jackson.annotation.JsonValue

enum class DossierDocumentType(@JsonValue val code: String) {
    WORK_CONTRACT("work_contract"),
    RESIDENTIAL_ADDRESS("residential_address"),  // FIXME this shouldn't be here according to io.wallee.payment.facilitator.snapswap.sdk.kyc.model.document.DocumentType
    QUESTIONS("questions")  // FIXME this shouldn't be here io.wallee.payment.facilitator.snapswap.sdk.kyc.model.document.DocumentType
}
