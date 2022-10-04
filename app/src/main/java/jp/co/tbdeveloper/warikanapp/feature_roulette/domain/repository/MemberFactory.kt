package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

object MemberFactory {
    fun create(id: Int = 0, rouletteId: Int, members: List<Member>): List<MemberEntity> {
        return List(members.size) { i ->
            MemberEntity(id, rouletteId, members[i].name, members[i].color)
        }
    }
}