package com.tbdeveloper.warikanapp.feature_roulette.domain.repository

import com.tbdeveloper.warikanapp.feature_roulette.domain.model.SettingsEntity

interface SettingsRepository {
    suspend fun getSettings(): SettingsEntity?
    suspend fun updateSettings(settingsEntity: SettingsEntity)
}