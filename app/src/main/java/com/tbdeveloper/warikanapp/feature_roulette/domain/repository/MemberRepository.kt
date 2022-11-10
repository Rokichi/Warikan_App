package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity

interface MemberRepository {
    fun getMembers(): List<MemberEntity>
    suspend fun getMembersById(id: Long): List<MemberEntity>?
    suspend fun insertMember(member: MemberEntity)
    suspend fun insertMembers(members: List<MemberEntity>)
    suspend fun deleteMember(member: MemberEntity)
    suspend fun deleteMembers(id: Long)
}