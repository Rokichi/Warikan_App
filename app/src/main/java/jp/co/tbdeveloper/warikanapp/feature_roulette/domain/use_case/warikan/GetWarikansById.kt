package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository

class GetWarikansById(
    private val repository: WarikanRepository
) {
    suspend operator fun invoke(id: Long): List<WarikanEntity>? {
        return repository.getWarikansById(id)
    }
}