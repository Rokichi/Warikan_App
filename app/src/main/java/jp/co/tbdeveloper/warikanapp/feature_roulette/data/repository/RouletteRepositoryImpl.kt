package jp.co.tbdeveloper.warikanapp.feature_roulette.data.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source.RouletteDao
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.RouletteEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteRepository
import kotlinx.coroutines.flow.Flow

class RouletteRepositoryImpl(
    private val dao: RouletteDao
) : RouletteRepository {
    override fun getRoulettes(): Flow<List<RouletteEntity>> {
        return dao.getRoulettes()
    }

    override suspend fun getRouletteById(id: Int): RouletteEntity? {
        return dao.getRouletteById(id)
    }

    override suspend fun insertRoulette(roulette: RouletteEntity):Long {
        return dao.insertRoulette(roulette)
    }

    override suspend fun deleteRoulette(roulette: RouletteEntity) {
        return dao.deleteRoulette(roulette)
    }
}