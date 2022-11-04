package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class GetMembersById(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(id: Long): List<MemberEntity>? {
        return repository.getMembersById(id)
    }
}