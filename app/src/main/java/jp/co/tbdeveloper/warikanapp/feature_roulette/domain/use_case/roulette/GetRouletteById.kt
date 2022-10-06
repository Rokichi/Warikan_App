package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository
import kotlinx.coroutines.flow.Flow

class GetRouletteById(
    private val repository: RouletteRepository
) {
    suspend operator fun invoke(id: Long): RouletteEntity? {
        return repository.getRouletteById(id)
    }
}