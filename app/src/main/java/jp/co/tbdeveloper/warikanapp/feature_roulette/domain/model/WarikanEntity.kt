package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WarikanEntity(
    @PrimaryKey val id: Int? = null,
    val RouletteId:Int,
    val ratios: String,
    val proportion: Int,
    val color: Int,
)

class InvalidWarikanException(message: String) : Exception(message)