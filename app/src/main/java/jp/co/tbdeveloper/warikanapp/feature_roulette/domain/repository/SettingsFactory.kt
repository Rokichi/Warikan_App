package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings


object SettingsFactory {
    /**
     * SettingsEntity -> Settings
     *
     * @param settingsEntity 変換したいエントリー
     * @return　Settings
     */
    fun create(
        settingsEntity: SettingsEntity,
    ): Settings {
        return Settings(
            autoSave = settingsEntity.autoSave,
            isMuted = settingsEntity.isMuted,
            setDarkTheme = settingsEntity.setDarkTheme
        )
    }
}
