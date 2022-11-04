package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository

class AddRoulette(
    private val repository: RouletteRepository
) {
    suspend operator fun invoke(roulette: RouletteEntity): Long {
        return repository.insertRoulette(roulette)
    }
}