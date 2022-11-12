package com.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity

@Dao
interface WarikanDao {
    /**
     * すべてのデータ取得
     *
     * @return　すべてのデータ
     */
    @Query("SELECT * FROM warikanentity")
    fun getAllWarikans(): List<WarikanEntity>

    /**
     * RouletteIDに紐付いたデータ取得
     *
     * @param id RouletteID
     * @return 該当IDをもつわりかん
     */
    @Query("SELECT * FROM warikanentity WHERE RouletteId = :id")
    fun getWarikansById(id: Long): List<WarikanEntity>

    /**
     * データ挿入
     *
     * @param warikan Entity
     */
    @Insert
    suspend fun insertWarikan(warikan: WarikanEntity)

    /**
     * 複数データ挿入
     *
     * @param warikans 複数データ
     */
    @Insert
    suspend fun insertWarikans(warikans: List<WarikanEntity>)

    /**
     * データ削除
     *
     * @param warikan 削除したいデータ
     */
    @Delete
    suspend fun deleteWarikan(warikan: WarikanEntity)

    /**
     * ルーレットのデータ削除に伴ってひも付きデータ削除
     *
     * @param id RouletteID
     */
    @Query("DELETE FROM warikanentity WHERE RouletteId = :id")
    suspend fun deleteWarikans(id: Long)
}