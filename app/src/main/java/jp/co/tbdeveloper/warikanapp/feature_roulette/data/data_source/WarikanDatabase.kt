package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity

/**
 * WarikanEntityDB
 *
 */
@Database(
    entities = [WarikanEntity::class],
    version = 1,
    exportSchema = false
)

abstract class WarikanDatabase() : RoomDatabase() {
    abstract val warikanDao: WarikanDao

    companion object {
        const val DATABASE_NAME = "warikans_db"
    }
}