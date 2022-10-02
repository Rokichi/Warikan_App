package jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import kotlinx.coroutines.flow.Flow

interface RouletteRepository {
    fun getRoulettes(): Flow<List<RouletteEntity>>
    suspend fun getRouletteById(id: Int): RouletteEntity?
    suspend fun insertRoulette(roulette: RouletteEntity)
    suspend fun deleteRoulette(roulette: RouletteEntity)
}