package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository

class DeleteWarikan(
    private val repository: WarikanRepository
) {
    suspend operator fun invoke(warikan: WarikanEntity) {
        repository.deleteWarikan(warikan)
    }

}