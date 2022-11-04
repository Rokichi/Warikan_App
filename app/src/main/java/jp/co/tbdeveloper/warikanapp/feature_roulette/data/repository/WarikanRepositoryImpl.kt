package jp.co.tbdeveloper.warikanapp.feature_roulette.data.repository

import jp.co.tbdeveloper.warikanapp.feature_roulette.data.data_source.WarikanDao
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.WarikanEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanRepository

class WarikanRepositoryImpl(
    private val dao: WarikanDao
) : WarikanRepository {
    override fun getWarikans(): List<WarikanEntity> {
        return dao.getAllWarikans()
    }

    override suspend fun getWarikansById(id: Long): List<WarikanEntity>? {
        return dao.getWarikansById(id)
    }

    override suspend fun insertWarikan(warikan: WarikanEntity) {
        dao.insertWarikan(warikan)
    }

    override suspend fun insertWarikans(warikans: List<WarikanEntity>) {
        dao.insertWarikans(warikans)
    }

    override suspend fun deleteWarikan(warikan: WarikanEntity) {
        dao.deleteWarikan(warikan)
    }

    override suspend fun deleteWarikans(id: Long) {
        dao.deleteWarikans(id)
    }

}