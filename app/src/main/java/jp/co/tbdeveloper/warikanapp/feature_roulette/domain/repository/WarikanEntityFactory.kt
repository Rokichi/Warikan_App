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
                warikans[i].ratios.size.toLong(),
                rouletteId,
                warikans[i].ratios.joinToString(":"),
                warikans[i].proportion,
                warikans[i].color
            )
        }
    }

    /**
     * WarikanList -> WarikanEntityList
     *
     * @param rouletteId ひも付きルーレットID
     * @param warikans 保存したいデータ
     * @param proportions 比率データ
     * @return WarikanEntityList
     */
    fun create(
        rouletteId: Long,
        warikans: List<Warikan>,
        proportions: List<String>
    ): List<WarikanEntity> {
        return List(warikans.size) { i ->
            WarikanEntity(
                0,
                warikans[i].ratios.size.toLong(),
                rouletteId,
                warikans[i].ratios.joinToString(":"),
                proportions[i].toInt(),
                warikans[i].color
            )
        }
    }
}