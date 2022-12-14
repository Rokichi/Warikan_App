package com.tbdeveloper.warikanapp.feature_roulette.data.repository

import com.tbdeveloper.warikanapp.feature_roulette.data.data_source.SettingsDao
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val dao: SettingsDao
) : SettingsRepository {
    override suspend fun getSettings(): SettingsEntity? {
        return dao.getSetting()
    }

    override suspend fun updateSettings(settingsEntity: SettingsEntity) {
        dao.updateSettings(settingsEntity)
    }
}