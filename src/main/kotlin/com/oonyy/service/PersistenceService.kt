package com.oonyy.service

import com.oonyy.model.internal.DossierData
import com.oonyy.model.internal.DossierKey
import io.micronaut.context.annotation.Bean
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

const val SNAP_SWAP_STATE_FILE_NAME: String = "SnapSwapState.txt"

@Bean
class PersistenceService {

    fun writeState(state: Map<DossierKey, DossierData>) {
        File(SNAP_SWAP_STATE_FILE_NAME).writeText(Json.encodeToString(state))
    }

    fun readState(): MutableMap<DossierKey, DossierData> {
        val state = File(SNAP_SWAP_STATE_FILE_NAME).readText()
        return Json.decodeFromString(state)
    }
}