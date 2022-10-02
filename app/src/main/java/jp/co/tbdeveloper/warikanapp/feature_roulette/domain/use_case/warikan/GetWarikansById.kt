package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository
import kotlinx.coroutines.flow.Flow

class GetWarikansById(
    private val repository: WarikanRepository
) {
    suspend operator fun invoke(id: Int): Flow<List<WarikanEntity>>? {
        return repository.getWarikansById(id)
    }
}