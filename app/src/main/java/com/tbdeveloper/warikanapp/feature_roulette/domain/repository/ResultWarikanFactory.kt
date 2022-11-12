package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.ResultWarikan

object ResultWarikanFactory {
    fun create(members: List<Member>): List<ResultWarikan> {
        return List(members.size) { i ->
            ResultWarikan(members[i].name, members[i].color)
        }
    }
}