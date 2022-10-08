package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

/**
 * Warikan -> WarikanEntity
 */
object WarikanEntityFactory {
    /**
     * WarikanList -> WarikanEntityList
     *
     * @param rouletteId ひも付きルーレットID
     * @param warikans 保存したいデータ
     * @return WarikanEntityList
     */
    fun create(rouletteId: Long, warikans: List<Warikan>): List<WarikanEntity> {
        return List(warikans.size) { i ->
            WarikanEntity(
                0,
                rouletteId,
                warikans[i].ratios,
                warikans[i].proportion,
                warikans[i].color
            )
        }
    }
}