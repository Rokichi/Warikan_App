package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.*
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RouletteDao {
    /**
     * すべてのデータ取得
     *
     * @return すべてのデータ
     */
    @Query("SELECT * FROM rouletteentity")
    fun getRoulettes(): Flow<List<RouletteEntity>>

    /**
     * 該当IDデータ取得
     *
     * @param id ID
     */
    @Query("SELECT * FROM rouletteentity WHERE id = :id")
    suspend fun getRouletteById(id: Long): RouletteEntity?

    /**
     * データ挿入
     *
     * @param roulette Entity
     * @return Long 割り振られたID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoulette(roulette: RouletteEntity): Long

    /**
     * データ削除
     * @param roulette Entity
     */
    @Delete
    suspend fun deleteRoulette(roulette: RouletteEntity)
}