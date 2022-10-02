package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidRouletteException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository

class AddRoulette(
    private val repository: RouletteRepository
) {

    @Throws(InvalidRouletteException::class)
    suspend operator fun invoke(roulette: Roulette){
        if(roulette.total > 0){
            throw InvalidRouletteException("Total must be over zero.")
        }
        repository.insertRoulette(roulette)
    }
}