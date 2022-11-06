package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsRepository

class UpdateSettings(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(settingsEntity: SettingsEntity) {
        repository.updateSettings(settingsEntity)
    }
}
