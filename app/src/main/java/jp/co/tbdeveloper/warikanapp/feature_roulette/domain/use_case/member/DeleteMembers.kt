package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class DeleteMembers(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteMembers(id)
    }
}