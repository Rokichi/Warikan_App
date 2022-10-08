package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import androidx.core.text.isDigitsOnly
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidRouletteException

class RouletteValidation{
    @Throws(InvalidRouletteException::class)
    operator fun invoke(total: String) {
        if (total.isEmpty()) throw InvalidRouletteException("Total must not be empty.")

        if (!total.isDigitsOnly()) throw InvalidRouletteException("Total must be only digit.")

        if (total.toInt() <= 0) throw InvalidRouletteException("Total must be over zero.")
    }
}
