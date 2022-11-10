package com.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DB保存用Entity
 *
 * @property id プライマリキー
 * @property RouletteId　ひも付きルーレットID
 * @property name メンバー名
 * @property color メンバーカラー
 */
@Entity
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val RouletteId: Long,
    val name: String,
    val color: Int,
)

class InvalidMemberException(message: String) : Exception(message)