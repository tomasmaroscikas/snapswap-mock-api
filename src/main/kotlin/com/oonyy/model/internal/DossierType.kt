package com.oonyy.model.internal

import com.oonyy.until.EnumFinder
import kotlinx.serialization.Serializable

@Serializable
enum class DossierType(val code: String) {

    LIMITED("customweb-limited"),
    BASIC("customweb-basic");

    companion object : EnumFinder<String, DossierType>(DossierType.values().associateBy { it.code })
}