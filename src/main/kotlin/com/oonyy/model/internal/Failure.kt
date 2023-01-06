package com.oonyy.model.internal

@kotlinx.serialization.Serializable
data class Failure(val code: String, val sub_code: String, val reason: String)
