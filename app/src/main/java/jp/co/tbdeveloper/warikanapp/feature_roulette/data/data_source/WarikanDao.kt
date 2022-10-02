package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WarikanDao {
    @Query("SELECT * FROM warikanentity")
    fun getAllWarikans(): Flow<List<WarikanEntity>>

    @Query("SELECT * FROM warikanentity WHERE RouletteId = :id")
    suspend fun getWarikansById(id: Int): Flow<List<WarikanEntity>>?

    @Insert
    suspend fun insertWarikan(warikan: WarikanEntity)

    @Delete
    suspend fun deleteWarikan(warikan: WarikanEntity)
}