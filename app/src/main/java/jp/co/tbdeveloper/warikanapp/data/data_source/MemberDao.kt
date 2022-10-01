package jp.co.tbdeveloper.warikanapp.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.co.tbdeveloper.warikanapp.domain.model.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Query("SELECT * FROM member")
    fun getAllMembers(): Flow<List<Member>>

    @Query("SELECT * FROM member WHERE id = :id")
    suspend fun getMembersById(id: Int): Flow<List<Member>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers()
}