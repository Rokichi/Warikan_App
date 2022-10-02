package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.*
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RouletteDao {
    @Query("SELECT * FROM rouletteentity")
    fun getRoulettes(): Flow<List<RouletteEntity>>

    @Query("SELECT * FROM rouletteentity WHERE id = :id")
    suspend fun getRouletteById(id: Int): RouletteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoulette(roulette: RouletteEntity)

    @Delete
    suspend fun deleteRoulette(roulette: RouletteEntity)
}