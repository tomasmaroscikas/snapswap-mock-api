package com.oonyy.faker

import com.github.javafaker.Faker
import com.oonyy.model.internal.DossierAmlCheck
import com.oonyy.model.internal.DossierAmlCheckHit
import com.oonyy.model.internal.DossierAmlCheckHitList
import com.oonyy.model.internal.DossierAmlCheckHitSimilarity
import jakarta.inject.Singleton

@Singleton
class AmlGenerator {

    companion object {

        private val faker: Faker = Faker()

        fun generateHits(): List<DossierAmlCheckHit> {
            val hit = DossierAmlCheckHit(
                "2023-01-01",
                true,
                "Custom reason",
                faker.name().firstName(),
                faker.name().lastName(),
                1965,
                faker.nation().nationality(),
                DossierAmlCheckHitList(faker.number().randomDigit(), faker.name().name(), "PEPS"),
                faker.number().randomDigit(),
                DossierAmlCheckHitSimilarity("high","high","high","high",))
            return listOf(hit)
        }

        fun generateAml(): Array<DossierAmlCheck> {
            val aml = DossierAmlCheck(
                faker.beer().name(),
                faker.name().lastName(),
                faker.date().birthday(),
                faker.idNumber().valid(),
                generateHits(),
                faker.country().countryCode3(),
                faker.name().firstName(),
                faker.demographic().sex(),
                "id_document",
                null
            )

            return arrayOf(aml)
        }
    }


}