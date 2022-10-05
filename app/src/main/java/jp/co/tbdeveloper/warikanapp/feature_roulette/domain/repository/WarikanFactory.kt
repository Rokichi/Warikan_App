package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

object WarikanFactory {
    fun create(id: Long = 0, rouletteId: Long, warikans: List<Warikan>): List<WarikanEntity> {
        return List(warikans.size) { i ->
            WarikanEntity(
                id,
                rouletteId,
                warikans[i].ratios,
                warikans[i].proportion,
                warikans[i].color
            )
        }
    }
}