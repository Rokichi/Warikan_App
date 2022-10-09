package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

/**
 * WarikanEntity -> Warikan
 */
object WarikanFactory {
    /**
     * WarikanEntityList -> WarikanList
     *
     * @param warikanEntities DBから取得してきたデータ
     * @return WarikanList
     */
    fun create(warikanEntities: List<WarikanEntity>): List<Warikan> {
        return List(warikanEntities.size) { i ->
            Warikan(
                warikanEntities[i].ratios.split(":"),
                warikanEntities[i].proportion,
                warikanEntities[i].color
            )
        }
    }
}