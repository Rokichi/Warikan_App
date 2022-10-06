package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

object WarikanFactory {
    fun create(warikanEntities: List<WarikanEntity>): List<Warikan> {
        return List(warikanEntities.size) { i ->
            Warikan(
                warikanEntities[i].ratios,
                warikanEntities[i].proportion,
                warikanEntities[i].color
            )
        }
    }
}