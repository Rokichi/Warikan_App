package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

data class WarikanUseCases(
    val getWarikansById: GetWarikansById,
    val getAllWarikans: GetAllWarikans,
    val deleteWarikan: DeleteWarikan,
    val deleteWarikans: DeleteWarikans,
    val addWarikan: AddWarikan
)
