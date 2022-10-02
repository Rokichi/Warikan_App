package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

sealed class MembersEvent {
    data class AddMembers(val value:Int): MembersEvent()
    object SaveRoulette: MembersEvent()
}