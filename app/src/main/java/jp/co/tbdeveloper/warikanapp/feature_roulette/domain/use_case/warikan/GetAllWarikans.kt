package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository
import kotlinx.coroutines.flow.Flow

class GetAllWarikans(
    private val repository: WarikanRepository
) {
    operator fun invoke(): Flow<List<WarikanEntity>>{
        return repository.getWarikans()
    }
}