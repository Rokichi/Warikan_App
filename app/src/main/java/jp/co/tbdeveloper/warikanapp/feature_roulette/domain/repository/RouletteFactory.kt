package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity

object RouletteFactory {
    fun create(id: Long = 0, total: Int): RouletteEntity {
        return RouletteEntity(id, total)
    }
}