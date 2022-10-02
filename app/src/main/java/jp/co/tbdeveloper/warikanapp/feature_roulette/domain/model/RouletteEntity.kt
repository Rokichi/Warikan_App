package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RouletteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val total: Int,
)

class InvalidRouletteException(message: String) : Exception(message)