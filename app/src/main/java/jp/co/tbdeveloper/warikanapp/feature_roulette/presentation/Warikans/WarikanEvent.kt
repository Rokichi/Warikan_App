package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.Warikans

import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.MemberEvent

sealed class WarikanEvent {
    object AddWarikanEvent: WarikanEvent()
    data class DeleteWarikanEvent(val index:Int):WarikanEvent()
    data class EditWarikanEvent(val value: String, val index:Int): WarikanEvent()
    data class EditProportionEvent(val value: String, val index:Int): WarikanEvent()
    object StartEvent: WarikanEvent()
}