package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model

import androidx.compose.ui.graphics.Color

data class Member(
    val name: String,
    val color: Int,
) {
    companion object {
        val memberColors = listOf(Color.Red, Color.Blue, Color.Green, Color.DarkGray)
    }
}
