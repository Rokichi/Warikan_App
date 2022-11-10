package com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsRepository

class GetSettings(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): SettingsEntity {
        return repository.getSettings() ?: SettingsEntity(
            autoSave = false,
            isMuted = false,
            isApproximate = false,
            setDarkTheme = 0
        )
    }
}
