package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Query("SELECT * FROM memberentity")
    fun getAllMembers(): Flow<List<MemberEntity>>

    @Query("SELECT * FROM memberentity WHERE RouletteId = :id")
    fun getMembersById(id: Long): Flow<List<MemberEntity>>?

    @Insert
    suspend fun insertMember(member: MemberEntity)

    @Insert
    suspend fun insertMembers(members: List<MemberEntity>)

    @Delete
    suspend fun deleteMember(member: MemberEntity)

    @Query("DELETE FROM memberentity WHERE RouletteId = :id")
    suspend fun deleteMembers(id: Long)
}