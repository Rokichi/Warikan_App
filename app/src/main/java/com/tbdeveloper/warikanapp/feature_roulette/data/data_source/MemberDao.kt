package com.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity

@Dao
interface MemberDao {
    /**
     * すべてのメンバー取得
     *
     * @return すべてのメンバー
     */
    @Query("SELECT * FROM memberentity")
    fun getAllMembers(): List<MemberEntity>

    /**
     * RouletteIDに紐付いたデータ取得
     *
     * @param id RouletteID
     * @return 該当IDをもつメンバー
     */
    @Query("SELECT * FROM memberentity WHERE RouletteId = :id")
    fun getMembersById(id: Long): List<MemberEntity>?

    /**
     * データ挿入
     *
     * @param member Entity
     */
    @Insert
    suspend fun insertMember(member: MemberEntity)

    /**
     * 複数データ挿入
     *
     * @param members Entityリスト
     */
    @Insert
    suspend fun insertMembers(members: List<MemberEntity>)

    /**
     * データ削除
     *
     * @param member Entity
     */
    @Delete
    suspend fun deleteMember(member: MemberEntity)

    /**
     * ルーレットのデータ削除に伴ってひも付きデータ削除
     *
     * @param id RouletteID
     */
    @Query("DELETE FROM memberentity WHERE RouletteId = :id")
    suspend fun deleteMembers(id: Long)
}