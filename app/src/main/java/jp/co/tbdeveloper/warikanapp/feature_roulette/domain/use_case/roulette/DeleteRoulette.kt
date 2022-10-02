package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository

class DeleteRoulette(
    private val repository: RouletteRepository
) {
    suspend operator fun invoke(roulette: RouletteEntity) {
        repository.deleteRoulette(roulette)
    }
}