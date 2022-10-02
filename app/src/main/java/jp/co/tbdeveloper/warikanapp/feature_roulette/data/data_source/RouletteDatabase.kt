package jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity


@Database(
    entities = [RouletteEntity::class],
    version = 1
)

abstract class RouletteDatabase() : RoomDatabase() {
    abstract val rouletteDao: RouletteDao

    companion object {
        const val DATABASE_NAME = "roulettes_db"
    }
}