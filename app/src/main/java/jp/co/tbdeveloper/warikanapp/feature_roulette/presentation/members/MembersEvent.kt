package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

sealed class MembersEvent {
    object AddMember: MembersEvent()
    object SaveRoulette: MembersEvent()
}