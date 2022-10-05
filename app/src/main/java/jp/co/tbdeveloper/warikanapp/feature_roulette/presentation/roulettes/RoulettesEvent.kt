package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette

sealed class RoulettesEvent {
    data class AddMember(val value:Int): RoulettesEvent()
    object SaveRoulette: RoulettesEvent()
}