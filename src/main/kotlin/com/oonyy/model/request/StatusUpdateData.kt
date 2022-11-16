package com.oonyy.model.request

import com.oonyy.model.internal.DossierEntryState

data class StatusUpdateData(var dossierId: String,
                            var type: String,
                            var status: DossierEntryState)
