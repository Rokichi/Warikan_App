package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemberEntity(
    @PrimaryKey val id: Int? = null,
    val RouletteId:Int,
    val name: String,
    val color: Int,
)

class InvalidMemberException(message: String) : Exception(message)