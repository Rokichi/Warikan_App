package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun getMembers(): Flow<List<MemberEntity>>
    suspend fun getMembersById(id: Int): Flow<List<MemberEntity>>?
    suspend fun insertMember(member: MemberEntity)
    suspend fun insertMembers(members: List<MemberEntity>)
    suspend fun deleteMember(member: MemberEntity)
    suspend fun deleteMembers(members: List<MemberEntity>)
}