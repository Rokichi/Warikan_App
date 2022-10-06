package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

object MemberFactory {
    fun create(memberEntities: List<MemberEntity>): List<Member> {
        return List(memberEntities.size) { i ->
            Member(memberEntities[i].name, memberEntities[i].color)
        }
    }
}