package com.tbdeveloper.warikanapp.feature_roulette.data.repository

import com.tbdeveloper.warikanapp.feature_roulette.data.data_source.MemberDao
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository

class MemberRepositoryImpl(
    private val dao: MemberDao
) : MemberRepository {
    override fun getMembers(): List<MemberEntity> {
        return dao.getAllMembers()
    }

    override suspend fun getMembersById(id: Long): List<MemberEntity>? {
        return dao.getMembersById(id)
    }

    override suspend fun insertMember(member: MemberEntity) {
        dao.insertMember(member)
    }

    override suspend fun insertMembers(members: List<MemberEntity>) {
        dao.insertMembers(members)
    }

    override suspend fun deleteMember(member: MemberEntity) {
        dao.deleteMember(member)
    }

    override suspend fun deleteMembers(id: Long) {
        dao.deleteMembers(id)
    }

}