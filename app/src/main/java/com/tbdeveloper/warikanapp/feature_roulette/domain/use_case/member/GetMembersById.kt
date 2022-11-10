package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class GetMembersById(
    private val repository: MemberRepository
) {
    suspend operator fun invoke(id: Long): List<MemberEntity>? {
        return repository.getMembersById(id)
    }
}