package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans

sealed class WarikanEvent {
    object AddWarikanEvent : WarikanEvent()
    data class DeleteWarikanEvent(val index: Int) : WarikanEvent()
    data class EditProportionEvent(val value: String, val index: Int) : WarikanEvent()
    data class EditWarikanEvent(val value: String, val index: Int, val num: Int) : WarikanEvent()
    object StartEvent : WarikanEvent()
}