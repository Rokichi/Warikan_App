package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import kotlinx.coroutines.flow.Flow

interface WarikanRepository {
    fun getWarikans(): List<WarikanEntity>
    suspend fun getWarikansById(id: Long): List<WarikanEntity>?
    suspend fun insertWarikan(warikan: WarikanEntity)
    suspend fun insertWarikans(warikans: List<WarikanEntity>)
    suspend fun deleteWarikan(warikan: WarikanEntity)
    suspend fun deleteWarikans(id: Long)
}