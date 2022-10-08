package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

/**
 * ルーレットデータ
 *
 * @property Total 合計金額
 * @property RouletteId ルーレットID
 * @property Members メンバーデータ
 * @property Warikans 割り勘データ
 */
data class Roulette(
    val Total: Int = 0,
    val RouletteId: Long = 0,
    val Members: List<Member> = mutableListOf(),
    val Warikans: List<Warikan> = mutableListOf(),
)