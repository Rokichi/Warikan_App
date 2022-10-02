package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository
import kotlinx.coroutines.flow.Flow

class GetRoulettes(
    private val repository: RouletteRepository
) {
    operator fun invoke(): Flow<List<RouletteEntity>> {
        return repository.getRoulettes()
    }
}