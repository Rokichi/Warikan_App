package com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * ルーレットデータ
 *
 * @property total 合計金額
 * @property rouletteId ルーレットID
 * @property members メンバーデータ
 * @property warikans 割り勘データ
 */
@Parcelize
data class Roulette(
    val total: Int = 0,
    val rouletteId: Long = 0,
    val members: List<Member> = listOf(),
    val warikans: List<Warikan> = listOf(),
) : Parcelable