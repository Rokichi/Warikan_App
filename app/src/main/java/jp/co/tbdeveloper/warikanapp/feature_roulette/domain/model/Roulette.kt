package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

data class Roulette(
    val total: Int,
    val Members: List<Member>,
    val Warikans: List<Warikan>,
)