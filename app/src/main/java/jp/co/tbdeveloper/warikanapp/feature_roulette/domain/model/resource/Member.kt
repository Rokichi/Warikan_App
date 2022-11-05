package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import jp.co.tbdeveloper.warikanapp.ui.theme.DarkMemberColors
import jp.co.tbdeveloper.warikanapp.ui.theme.LightMemberColors
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
        fun memberColors(isDarKMode: Boolean): List<Color> {
            return if (isDarKMode) DarkMemberColors else LightMemberColors
        }
    }
}
