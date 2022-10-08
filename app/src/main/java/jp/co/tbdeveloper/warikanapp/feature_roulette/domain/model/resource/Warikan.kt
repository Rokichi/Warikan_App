package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

/**
 * 割り勘データ
 *
 * @property ratios わりかんの割合 "x:x:..."
 * @property proportion ルーレットの割合
 * @property color 背景色
 */
data class Warikan(
    val ratios: String,
    val proportion: Int,
    val color: Int,
)
