package jp.co.tbdeveloper.warikanapp.data.data_source

import androidx.room.Database
import jp.co.tbdeveloper.warikanapp.domain.model.Roulette


@Database(
    entities = [Roulette::class],
    version = 1
)

abstract class RouletteDatabase() {
    abstract val rouletteDao: RouletteDao
}