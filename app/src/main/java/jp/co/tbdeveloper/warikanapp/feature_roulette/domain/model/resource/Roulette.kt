package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

data class Roulette(
    val total: Int,
    val MemberId:Int,
    val Members: MutableList<Member>,
    val WarikanId:Int,
    val Warikans: MutableList<Warikan>,
)