package com.oonyy.response

class ResponseData {

    companion object {
        fun DOSSIER_STATUS_PHONE(phoneStatus: String): String {

            return """{
                  "email": "pending",
                  "enterprise": "pending",
                  "consistency": "pending",
                  "questions": {
                    "representative_nationality": "success",
                    "representative_capacity": "success"
                  },
                  "residential_address": "confirming",
                  "id_document": "pending",
                  "documents": {
                    "residential_address": "pending"
                  },
                  "id": "63377b59-4603-11ed-8a38-bd275b666d68",
                  "delivery": "pending",
                  "group": {
                    "dossiers": [
                      "63377b59-4603-11ed-8a38-bd275b666d68"
                    ],
                    "id": "63377b59-4603-11ed-8a38-bd275b666d68"
                  },
                  "phone": "$phoneStatus",
                  "failures": {
                    "phone": {
                        "code": "check_failed",
                        "sub_code": "sub_code",
                        "reason": "Phone check failed. WAL-81777 testing."
                        }
                    }
                }"""
        }

        const val DOSSIER_STATUS = """{
                  "email": "pending",
                  "enterprise": "pending",
                  "consistency": "pending",
                  "questions": {
                    "representative_nationality": "success",
                    "representative_capacity": "success"
                  },
                  "residential_address": "confirming",
                  "id_document": "pending",
                  "documents": {
                    "residential_address": "pending"
                  },
                  "id": "63377b59-4603-11ed-8a38-bd275b666d68",
                  "delivery": "pending",
                  "group": {
                    "dossiers": [
                      "63377b59-4603-11ed-8a38-bd275b666d68"
                    ],
                    "id": "63377b59-4603-11ed-8a38-bd275b666d68"
                  },
                  "phone": "pending"
                }"""

        const val DOSSIER_DATA = """{
                    "customer_id": "ORGANIZATION_2_46695204",
                    "invited": [],
                    "started_at": "2022-11-08T10:16:15.191Z",
                    "documents": [],
                    "id": "60a50b28-5f4e-11ed-a17f-6bc86b422fba",
                    "group": {
                        "dossiers": {
                            "60a50b28-5f4e-11ed-a17f-6bc86b422fba": {}
                        },
                        "id": "60a50b28-5f4e-11ed-a17f-6bc86b422fba"
                    }
                }"""

        const val BEARER_TOKEN = """{
                    "auth": {
                        "scheme": "Bearer",
                        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkb3NzaWVySWQiOiI2MGE1MGIyOC01ZjRlLTExZWQtYTE3Zi02YmM4NmI0MjJmYmEiLCJjbGllbnRJZCI6ImN1c3RvbXdlYi1iYXNpYyIsImV4cCI6MTY2ODUwNzM3NTAwMCwiaWF0IjoxNjY3OTAyNTc1MDAwLCJzdWIiOiI2MGE2YjhkYS01ZjRlLTExZWQtYTE3Zi0xYjI5YWZhYTFiZTIiLCJpc3MiOiJzbmFwc3dhcCJ9.7fOI5jWh0TGKcqKz7oaOYilTFClXT4o8xkalw2-4nhRPUpu-_PZjEZBjYKTpMzipw3-DRiqL4OmRdVoGylZacQ"
                    }
                }"""

        const val ALLOWED_DOCUMENTS = """[{"allowed":[{"back":false,"doc_type":"passport"},{"back":false,"doc_type":"driving_license"},{"back":true,"doc_type":"id_card"}],"issuing_country":"LTU"},{"allowed":[{"back":false,"doc_type":"passport"}],"issuing_country":"CYM"},{"allowed":[{"back":false,"doc_type":"passport"}],"issuing_country":"PNG"},{"allowed":[{"back":false,"doc_type":"passport"},{"back":true,"doc_type":"driving_license"},{"back":true,"doc_type":"id_card"}],"issuing_country":"GHA"}]"""
    }
}