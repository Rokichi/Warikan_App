package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow

class GetMembersById(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(id: Int): Flow<List<MemberEntity>>?{
        return repository.getMembersById(id)
    }
}