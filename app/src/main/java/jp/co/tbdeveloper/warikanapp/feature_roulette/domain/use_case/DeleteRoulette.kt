package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository

class DeleteRoulette(
    private val repository: RouletteRepository
) {
    suspend operator fun invoke(roulette: Roulette) {
        repository.deleteRoulette(roulette)
    }
}