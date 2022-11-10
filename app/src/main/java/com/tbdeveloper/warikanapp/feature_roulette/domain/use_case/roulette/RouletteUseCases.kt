package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette

data class RouletteUseCases(
    val getRoulettes: GetRoulettes,
    val deleteRoulette: DeleteRoulette,
    val addRoulette: AddRoulette,
    val getRouletteById: GetRouletteById,
    val rouletteValidation: RouletteValidation,
    val getRouletteResultIndex: GetRouletteResultIndex,
    val getResultDeg: GetResultDeg,
    val getWarikanResult: GetWarikanResult,
)