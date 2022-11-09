package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings

sealed class SettingsEvent {
    data class OnAutoSaveChange(val flg: Boolean) : SettingsEvent()
    data class OnIsMutedChange(val flg: Boolean) : SettingsEvent()
    data class OnSetDarkThemeSelect(val value: Int) : SettingsEvent()
    data class OnIsApproximateChange(val flg: Boolean) : SettingsEvent()
    object OnSave : SettingsEvent()
}
