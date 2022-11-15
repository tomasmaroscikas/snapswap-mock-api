package com.oonyy.model.internal

data class DossierLimitedStatus(val email: DossierEntryState,
                                val enterprise: DossierEntryState,
                                val consistency: DossierEntryState,
                                val questions: Map<DossierQuestion, DossierEntryState>,
                                val residential_address: DossierEntryState,
                                val id_document: DossierEntryState,
                                val documents: Map<DossierDocument, DossierEntryState>,
                                val id: String,
                                val delivery: DossierEntryState,
                                val group: String,
                                val phone: DossierEntryState,
                                val failures: Map<String, Failure>)
