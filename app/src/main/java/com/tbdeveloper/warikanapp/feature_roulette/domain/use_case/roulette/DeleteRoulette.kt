package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository

class DeleteRoulette(
    private val repository: RouletteRepository
) {
    suspend operator fun invoke(roulette: RouletteEntity) {
        repository.deleteRoulette(roulette)
    }
}