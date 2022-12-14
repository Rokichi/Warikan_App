package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidWarikanException
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository

class AddWarikan(
    private val repository: WarikanRepository
) {
    @Throws(InvalidWarikanException::class)
    suspend operator fun invoke(warikan: WarikanEntity) {
        if (warikan.ratios.isBlank()) {
            throw InvalidWarikanException("The ratios of warikan can't be empty.")
        }
        // 0以外の数値
        val regex = Regex("[1-9][0-9]*(:[1-9][0-9]*)+$")
        if (!regex.containsMatchIn(warikan.ratios)) {
            throw InvalidWarikanException("The ratios of warikan must be formatted like _:_:....")
        }
        repository.insertWarikan(warikan)
    }

    @Throws(InvalidWarikanException::class)
    suspend operator fun invoke(warikans: List<WarikanEntity>) {
        for (warikan in warikans) {
            if (warikan.ratios.isBlank()) {
                throw InvalidWarikanException("The ratios of warikan can't be empty.")
            }
            // 0以外の数値
            val regex = Regex("[1-9][0-9]*(:[1-9][0-9]*)+$")
            if (!regex.containsMatchIn(warikan.ratios)) {
                throw InvalidWarikanException("The ratios of warikan must be formatted like _:_:....")
            }
        }
        repository.insertWarikans(warikans)
    }

}