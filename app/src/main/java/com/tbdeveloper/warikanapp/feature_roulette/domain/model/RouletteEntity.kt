package com.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DB保存用Entity
 *
 * @property id プライマリキー
 * @property total 合計金額
 */
@Entity
data class RouletteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val total: Int,
)

class InvalidRouletteException(message: String) : Exception(message)