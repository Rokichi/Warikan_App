package jp.co.tbdeveloper.warikanapp.domain.use_case

import jp.co.tbdeveloper.warikanapp.domain.model.Roulette
import jp.co.tbdeveloper.warikanapp.domain.repository.RouletteRepository

class DeleteRoulette(
    private val repository: RouletteRepository
) {
    suspend operator fun invoke(roulette: Roulette) {
        repository.deleteRoulette(roulette)
    }
}