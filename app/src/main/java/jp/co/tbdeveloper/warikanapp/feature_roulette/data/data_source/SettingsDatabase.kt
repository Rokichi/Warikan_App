package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity

/**
 * SettingsEntityDB
 *
 */
@Database(
    entities = [SettingsEntity::class],
    version = 1,
    exportSchema = false
)

abstract class SettingsDatabase() : RoomDatabase() {
    abstract val settingsDao: SettingsDao

    companion object {
        const val DATABASE_NAME = "settings_db"
    }
}