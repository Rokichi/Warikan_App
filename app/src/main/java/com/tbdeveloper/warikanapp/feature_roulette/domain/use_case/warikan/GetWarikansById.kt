package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository

class GetWarikansById(
    private val repository: WarikanRepository
) {
    suspend operator fun invoke(id: Long): List<WarikanEntity>? {
        return repository.getWarikansById(id)
    }
}