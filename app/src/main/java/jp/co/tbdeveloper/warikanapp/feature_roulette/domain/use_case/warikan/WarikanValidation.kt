package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import androidx.core.text.isDigitsOnly
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidWarikanException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

class WarikanValidation {
    @Throws(InvalidWarikanException::class)
    operator fun invoke(warikans: List<Warikan>, proportions: List<String>) {
        for (warikan in warikans) {
            if (warikan.ratios.any { it.isEmpty() }) {
                throw  InvalidWarikanException("ratios must not be empty.")
            }
            if (warikan.ratios.any { !it.isDigitsOnly() || it.toInt() < 0 }) {
                throw  InvalidWarikanException("ratios must be only digits.")
            }
            if (warikan.ratios.all { it == "0" }) {
                throw  InvalidWarikanException("at least one ratio must be over 0")
            }
        }
        if (proportions.any { it.isEmpty() }) {
            throw  InvalidWarikanException("proportions must not be empty.")
        }
        if (proportions.any { !it.isDigitsOnly() || it.toInt() <= 0 }) {
            throw  InvalidWarikanException("proportions must be only digits.")
        }
    }
}