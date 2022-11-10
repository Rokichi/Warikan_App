package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

/**
 * WarikanEntity -> Warikan
 */
object WarikanFactory {
    fun create(size: Long, warikanEntities: List<WarikanEntity>): List<List<Warikan>> {
        val warikansMap = mutableMapOf<Long, MutableList<Warikan>>()
        for (warikanEntity in warikanEntities) {
            if (warikanEntity.memberSize != size) continue
            val id = warikanEntity.RouletteId
            val warikan = convert2Warikan(warikanEntity)
            if (warikansMap.containsKey(id)) {
                warikansMap[id]?.add(warikan)
            } else {
                warikansMap[id] = mutableListOf(warikan)
            }
        }
        val result = warikansMap.toSortedMap()
        return result.map { it.value }
    }

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

    private fun convert2Warikan(warikanEntity: WarikanEntity): Warikan {
        return Warikan(
            warikanEntity.ratios.split(":"),
            warikanEntity.proportion,
            warikanEntity.color
        )
    }
}
