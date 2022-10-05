package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val RouletteId: Long,
    val name: String,
    val color: Int,
)

class InvalidMemberException(message: String) : Exception(message)