package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

sealed class RoulettesEvent {
    object StartClickEvent : RoulettesEvent()
    data class InitRouletteEvent(val id: Long) : RoulettesEvent()
}