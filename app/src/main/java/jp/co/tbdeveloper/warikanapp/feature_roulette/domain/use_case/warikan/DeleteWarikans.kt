package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository

class DeleteWarikans(
    private val repository: WarikanRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteWarikans(id)
    }

}