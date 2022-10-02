package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette

data class RoulettesState(
    val roulettes: List<Roulette> = emptyList(),
    val roulette: Roulette? = null,
)