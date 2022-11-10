package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings


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
            isApproximate = settings.isApproximate,
            setDarkTheme = settings.setDarkTheme
        )
    }
}
