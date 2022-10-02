package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member

data class MembersState(
    val Members: MutableList<Member> = mutableListOf(),
)