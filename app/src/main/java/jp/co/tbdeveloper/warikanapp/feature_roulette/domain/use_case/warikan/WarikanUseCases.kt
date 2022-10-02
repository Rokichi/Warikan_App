package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan

data class WarikanUseCases(
    val getWarikansById: GetWarikansById,
    val deleteWarikan: DeleteWarikan,
    val addWarikan: AddWarikan
)
