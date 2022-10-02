package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette

sealed class MembersEvent {
    data class DeleteMember(val roulette: Roulette) : MembersEvent()
}