package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RouletteEntity(
    val total: Int,
    val MembersId: Int,
    val WarikanId: Int,
    @PrimaryKey val id: Int? = null
)

class InvalidRouletteException(message: String) : Exception(message)