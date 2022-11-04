package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize

/**
 * メンバーデータ
 *
 * @property name 名前
 * @property color 色
 */
@Parcelize
data class Member(
    val name: String,
    val color: Int,
) : Parcelable {
    companion object {
        val memberColors = listOf(Color.Red, Color.Blue, Color.Green, Color.DarkGray)
    }
}
