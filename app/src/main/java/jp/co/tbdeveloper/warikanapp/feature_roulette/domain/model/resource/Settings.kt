package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource

data class Settings(
    val autoSave: Boolean,
    val isMuted: Boolean,
    val setDarkTheme: Int
) {
    companion object {
        val darkThemeText = listOf("通常", "ダークテーマ", "デバイスのテーマ")
    }
}