package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings


object SettingsEntityFactory {
    /**
     * Settings -> SettingsEntity
     *
     * @param settings 変換したいSettings
     * @return　SettingsEntity
     */
    fun create(
        settings: Settings,
    ): SettingsEntity {
        return SettingsEntity(
            autoSave = settings.autoSave,
            isMuted = settings.isMuted,
            setDarkTheme = settings.setDarkTheme
        )
    }
}
