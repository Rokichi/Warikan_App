package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

/**
 * RouletteEntity -> Roulette
 */
object RouletteFactory {
    /**
     * RouletteEntity -> Roulette
     *
     * @param rouletteEntity DBから取得したEntity
     * @param memberEntities DBから取得したMemberEntities
     * @param warikanEntities DBから取得したWarikanEntities
     * @return Roulette
     */
    fun create(
        rouletteEntity: RouletteEntity,
        memberEntities: List<Member>,
        warikanEntities: List<Warikan>
    ): Roulette {
        return Roulette(
            total = rouletteEntity.total,
            rouletteId = rouletteEntity.id,
            members = memberEntities,
            warikans = warikanEntities,
        )
    }
}