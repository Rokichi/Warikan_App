package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

data class Roulette(
    val Total: Int = 0,
    val RouletteId: Long = 0,
    val Members: List<Member> = mutableListOf(),
    val Warikans: List<Warikan> = mutableListOf(),
)