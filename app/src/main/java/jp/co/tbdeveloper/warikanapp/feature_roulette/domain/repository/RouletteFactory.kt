package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

object RouletteFactory {
    fun create(
        rouletteEntity: RouletteEntity,
        memberEntities: List<Member>,
        warikanEntities: List<Warikan>
    ): Roulette {
        return Roulette(
            Total = rouletteEntity.total,
            RouletteId = rouletteEntity.id,
            Members = memberEntities,
            Warikans = warikanEntities,
        )
    }
}