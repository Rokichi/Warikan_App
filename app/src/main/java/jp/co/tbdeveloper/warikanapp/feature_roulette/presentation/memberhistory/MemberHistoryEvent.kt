package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.memberhistory

sealed class MemberHistoryEvent {
    data class OnItemClick(val index: Int) : MemberHistoryEvent()
}