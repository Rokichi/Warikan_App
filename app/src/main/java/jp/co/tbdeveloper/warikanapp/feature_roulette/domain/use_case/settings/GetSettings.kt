package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsRepository

class GetSettings(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): SettingsEntity {
        return repository.getSettings() ?: SettingsEntity(
            autoSave = false,
            isMuted = false,
            setDarkTheme = 0
        )
    }
}