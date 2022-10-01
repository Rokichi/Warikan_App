package jp.co.tbdeveloper.warikanapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Roulette(
    val total: Int,
    val MembersId: Int,
    val WarikanId: Int,
    @PrimaryKey val id: Int? = null
)
