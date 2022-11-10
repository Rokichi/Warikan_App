package com.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity

@Dao
interface SettingsDao {
    /**
     * データ取得
     *
     * @return　単一データ
     */
    @Query("SELECT * FROM settingsentity WHERE id=0")
    fun getSetting(): SettingsEntity?

    /**
     * データ更新
     *
     * @param settings Entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: SettingsEntity)
}