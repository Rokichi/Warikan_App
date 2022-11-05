package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

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