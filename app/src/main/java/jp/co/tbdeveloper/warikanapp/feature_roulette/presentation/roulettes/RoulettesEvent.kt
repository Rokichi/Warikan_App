package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

sealed class RoulettesEvent {
    object StartClickEvent : RoulettesEvent()
    object StopClickEvent : RoulettesEvent()
    object RetryClickEvent : RoulettesEvent()
    object GoHomeClickEvent : RoulettesEvent()
    object EndRouletteEvent : RoulettesEvent()
    data class EditRatioButtonClick(val index: Int, val flg: Boolean) : RoulettesEvent()
    data class MuteClickEvent(val isMuted: Boolean) : RoulettesEvent()
}