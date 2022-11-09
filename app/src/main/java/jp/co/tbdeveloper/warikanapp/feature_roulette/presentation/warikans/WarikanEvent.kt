package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

sealed class WarikanEvent {
    object AddWarikanEvent : WarikanEvent()
    data class DeleteWarikanEvent(val index: Int) : WarikanEvent()
    data class EditWarikanEvent(val value: String, val index: Int, val num: Int) : WarikanEvent()
    object StartEvent : WarikanEvent()
    object HistoryClickEvent : WarikanEvent()
    data class LoadWarikansEvent(val warikans: List<Warikan>) : WarikanEvent()
}