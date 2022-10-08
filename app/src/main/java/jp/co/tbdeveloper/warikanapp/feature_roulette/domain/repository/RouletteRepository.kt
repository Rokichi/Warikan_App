package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import kotlinx.coroutines.flow.Flow

interface RouletteRepository {
    fun getRoulettes(): Flow<List<RouletteEntity>>
    suspend fun getRouletteById(id: Long): RouletteEntity?
    suspend fun insertRoulette(roulette: RouletteEntity): Long
    suspend fun deleteRoulette(roulette: RouletteEntity)
}