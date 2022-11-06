package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings

sealed class SettingsEvent {
    data class onAutoSaveChange(val flg: Boolean) : SettingsEvent()
    data class onIsMutedChange(val flg: Boolean) : SettingsEvent()
    data class onSetDarkThemeSelect(val value: Int) : SettingsEvent()
    object onSave : SettingsEvent()
}
