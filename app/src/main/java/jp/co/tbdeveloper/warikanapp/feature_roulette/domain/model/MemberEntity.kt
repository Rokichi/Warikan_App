package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val RouletteId: Int,
    val name: String,
    val color: Int,
)

class InvalidMemberException(message: String) : Exception(message)