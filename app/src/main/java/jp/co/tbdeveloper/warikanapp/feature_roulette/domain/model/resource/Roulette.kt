package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

data class Roulette(
    val total: Int,
    val Members: List<Member>,
    val Warikans: List<Warikan>,
)