package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.Roulette

sealed class RoulettesEvent {
    data class DeleteRoulette(val roulette: Roulette) : RoulettesEvent()
    object RestoreRoulette: RoulettesEvent()
}