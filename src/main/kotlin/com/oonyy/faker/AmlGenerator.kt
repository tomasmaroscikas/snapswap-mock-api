package com.oonyy.faker

import com.github.javafaker.Faker
import com.oonyy.model.internal.*
import jakarta.inject.Singleton
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

@Singleton
class AmlGenerator {

    companion object {

        private val faker: Faker = Faker()

        fun generateAml(dossierType: DossierType): Array<DossierAmlCheck> {
            return if (dossierType == DossierType.LIMITED) {
                arrayOf(generateAmlRepresentative(), generateAmlEnterprise())
            } else {
                arrayOf(generateAmlUbo())
            }
        }

        private fun generateAmlRepresentative(): DossierAmlCheck {
            return DossierAmlCheck(
                provider = faker.beer().name(),
                familyName = faker.name().lastName(),
                birthDate = "1998-08-29",
                id = faker.idNumber().valid(),
                hits = generateHits(),
                countryOfResidence = faker.country().countryCode3().uppercase(),
                givenName = faker.name().firstName(),
                gender = faker.demographic().sex().lowercase(),
                referenceEntry = "id_document"
            )
        }

        private fun generateAmlEnterprise(): DossierAmlCheck {
            return DossierAmlCheck(
                hits = generateHits(),
                name = faker.company().name(),
                referenceEntry = "enterprise"
            )
        }

        private fun generateAmlUbo(): DossierAmlCheck {
            return DossierAmlCheck(
                provider = faker.beer().name(),
                familyName = faker.name().lastName(),
                birthDate = "1998-08-29",
                id = faker.idNumber().valid(),
                hits = generateHits(),
                countryOfResidence = faker.country().countryCode3().uppercase(),
                givenName = faker.name().firstName(),
                gender = faker.demographic().sex().lowercase(),
                referenceEntry = "questions",
                referenceEntryId = "question_1"
            )
        }

        private fun generateHits(): List<DossierAmlCheckHit> {
            val hit = DossierAmlCheckHit(
                ZonedDateTime.now(ZoneOffset.UTC),
                true,
                "Custom reason",
                faker.name().firstName(),
                faker.name().lastName(),
                1965,
                faker.country().countryCode3().uppercase(),
                DossierAmlCheckHitList(faker.number().randomDigit(), faker.name().name(), "PEPS"),
                faker.number().randomDigit(),
                DossierAmlCheckHitSimilarity("high","high","high","high",))
            return listOf(hit)
        }
    }
}