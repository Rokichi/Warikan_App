package jp.co.tbdeveloper.warikanapp.feature_roulette.data.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source.MemberDao
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow

class MemberRepositoryImpl(
    private val dao: MemberDao
) : MemberRepository {
    override fun getMembers(): Flow<List<MemberEntity>> {
        return dao.getAllMembers()
    }

    override suspend fun getMembersById(id: Int): Flow<List<MemberEntity>>? {
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

    override suspend fun deleteMembers(members: List<MemberEntity>) {
        for (member in members) {
            dao.deleteMember(member)
        }
    }

}