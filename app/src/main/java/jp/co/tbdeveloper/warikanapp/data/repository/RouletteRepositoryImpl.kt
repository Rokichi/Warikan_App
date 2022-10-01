package jp.co.tbdeveloper.warikanapp.data.repository

import jp.co.tbdeveloper.warikanapp.data.data_source.RouletteDao
import jp.co.tbdeveloper.warikanapp.domain.model.Roulette
import jp.co.tbdeveloper.warikanapp.domain.repository.RouletteRepository
import kotlinx.coroutines.flow.Flow

class RouletteRepositoryImpl(
    private val dao: RouletteDao
) : RouletteRepository {
    override fun getRoulettes(): Flow<List<Roulette>> {
        return dao.getRoulettes()
    }

    override suspend fun getRouletteById(id: Int): Roulette? {
        return dao.getRouletteById(id)
    }

    override suspend fun insertRoulette(roulette: Roulette) {
        return dao.insertRoulette(roulette)
    }

    override suspend fun deleteRoulette(roulette: Roulette) {
        return dao.deleteRoulette(roulette)
    }
}