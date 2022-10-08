package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.ResultWarikan

object ResultWarikanFactory {
    fun create(members: List<Member>): List<ResultWarikan> {
        return List(members.size) { i ->
            ResultWarikan(members[i].name, members[i].color)
        }
    }
}