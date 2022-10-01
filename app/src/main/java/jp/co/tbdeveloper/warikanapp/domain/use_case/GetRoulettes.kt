package jp.co.tbdeveloper.warikanapp.domain.use_case

import jp.co.tbdeveloper.warikanapp.domain.model.Roulette
import jp.co.tbdeveloper.warikanapp.domain.repository.RouletteRepository
import kotlinx.coroutines.flow.Flow

class GetRoulettes(
    private val repository: RouletteRepository
) {
    operator fun invoke(): Flow<List<Roulette>> {
        return repository.getRoulettes()
    }
}