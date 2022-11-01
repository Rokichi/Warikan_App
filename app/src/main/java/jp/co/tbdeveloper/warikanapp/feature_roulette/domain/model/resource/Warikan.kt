package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 割り勘データ
 *
 * @property ratios わりかんの割合
 * @property proportion ルーレットの割合
 * @property color 背景色
 */
@Parcelize
data class Warikan(
    val ratios: List<String>,
    val proportion: Int,
    val color: Int,
):Parcelable
