package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import kotlinx.coroutines.flow.Flow

interface WarikanRepository {
    fun getWarikans(): Flow<List<WarikanEntity>>
    suspend fun getWarikansById(id: Int): Flow<List<WarikanEntity>>?
    suspend fun insertWarikan(warikan: WarikanEntity)
    suspend fun deleteWarikan(warikan: WarikanEntity)
}