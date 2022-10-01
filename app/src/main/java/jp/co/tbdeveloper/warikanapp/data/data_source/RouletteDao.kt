package jp.co.tbdeveloper.warikanapp.data.data_source

import androidx.room.*
import jp.co.tbdeveloper.warikanapp.domain.model.Roulette
import kotlinx.coroutines.flow.Flow

@Dao
interface RouletteDao {
    @Query("SELECT * FROM roulette")
    fun getRoulettes(): Flow<List<Roulette>>

    @Query("SELECT * FROM member WHERE id = :id")
    suspend fun getRouletteById(id: Int): Roulette?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoulette(roulette: Roulette)

    @Delete
    suspend fun deleteRoulette(roulette: Roulette)
}