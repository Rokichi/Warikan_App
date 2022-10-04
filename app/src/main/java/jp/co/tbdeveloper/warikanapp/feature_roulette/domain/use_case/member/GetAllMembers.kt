package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow

class GetAllMembers(
    private val repository: MemberRepository
) {
    operator fun invoke(): Flow<List<MemberEntity>>{
        return repository.getMembers()
    }
}