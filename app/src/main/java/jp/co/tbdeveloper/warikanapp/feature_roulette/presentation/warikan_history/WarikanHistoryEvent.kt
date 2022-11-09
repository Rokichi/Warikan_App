package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history

sealed class WarikanHistoryEvent {
    data class OnItemClick(val index: Int) : WarikanHistoryEvent()
}
