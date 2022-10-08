package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

import androidx.compose.ui.graphics.Color

/**
 * メンバーデータ
 *
 * @property name 名前
 * @property color 色
 */
data class Member(
    val name: String,
    val color: Int,
) {
    companion object {
        val memberColors = listOf(Color.Red, Color.Blue, Color.Green, Color.DarkGray)
    }
}
