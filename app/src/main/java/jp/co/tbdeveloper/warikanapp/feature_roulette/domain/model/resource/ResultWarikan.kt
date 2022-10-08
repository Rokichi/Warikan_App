package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

/**
 * 割り勘結果データ
 *
 * @property name 名前
 * @property color 色
 * @property proportion 払う割合
 * @property payment 支払額
 */
data class ResultWarikan(
    val name: String = "",
    val color: Int,
    val proportion: Int = 0,
    val payment: Double = 0.0,
)
