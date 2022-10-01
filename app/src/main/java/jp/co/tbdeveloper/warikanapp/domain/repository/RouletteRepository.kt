package jp.co.tbdeveloper.warikanapp.domain.repository

import jp.co.tbdeveloper.warikanapp.domain.model.Roulette
import kotlinx.coroutines.flow.Flow

interface RouletteRepository {
    fun getRoulettes(): Flow<List<Roulette>>

    suspend fun getRouletteById(id: Int): Roulette?
    suspend fun insertRoulette(roulette: Roulette)
    suspend fun deleteRoulette(roulette: Roulette)
}