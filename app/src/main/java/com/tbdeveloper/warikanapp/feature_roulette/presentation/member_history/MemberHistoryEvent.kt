package com.tbdeveloper.warikanapp.feature_roulette.presentation.member_history

sealed class MemberHistoryEvent {
    data class OnItemClick(val index: Int) : MemberHistoryEvent()
}