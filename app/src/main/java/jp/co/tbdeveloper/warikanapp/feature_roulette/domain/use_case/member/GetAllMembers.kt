package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class GetAllMembers(
    private val repository: MemberRepository
) {
    operator fun invoke(): List<MemberEntity> {
        return repository.getMembers()
    }
}