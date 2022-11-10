package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity

/**
 * Roulette -> RouletteEntity
 */
object RouletteEntityFactory {
    /**
     * Roulette -> RouletteEntity
     *
     * @param total 合計金額
     * @return RouletteEntity
     */
    fun create(total: Int): RouletteEntity {
        return RouletteEntity(0, total)
    }
}