package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository

class GetAllWarikans(
    private val repository: WarikanRepository
) {
    operator fun invoke(): List<WarikanEntity> {
        return repository.getWarikans()
    }
}