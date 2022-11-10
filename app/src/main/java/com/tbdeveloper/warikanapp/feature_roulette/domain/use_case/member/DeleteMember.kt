package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class DeleteMember(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(member: MemberEntity) {
        repository.deleteMember(member)
    }
}