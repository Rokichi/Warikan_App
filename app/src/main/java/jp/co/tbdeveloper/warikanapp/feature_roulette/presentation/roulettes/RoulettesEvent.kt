package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

sealed class RoulettesEvent {
    object StartClickEvent : RoulettesEvent()
    object StopClickEvent : RoulettesEvent()
    object EndRouletteEvent : RoulettesEvent()
}