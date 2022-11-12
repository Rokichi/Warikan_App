package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class AddMember(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(member: MemberEntity) {
        repository.insertMember(member)
    }

    suspend operator fun invoke(members: List<MemberEntity>) {
        repository.insertMembers(members)
    }
}