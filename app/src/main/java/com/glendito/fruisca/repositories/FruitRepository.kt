package com.glendito.fruisca.repositories

import com.glendito.fruisca.database.daos.FruitDao
import com.glendito.fruisca.database.entities.FruitEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface FruitRepository {
    suspend fun getAll(): List<FruitEntity>
    suspend fun insert(fruitEntity: FruitEntity)
}

class FruitRepositoryImpl(
    private val fruitDao: FruitDao,
    private val dispatcher: CoroutineDispatcher
): FruitRepository {
    override suspend fun getAll(): List<FruitEntity> {
        return withContext(dispatcher) {
            fruitDao.getAll()
        }
    }

    override suspend fun insert(fruitEntity: FruitEntity) {
        withContext(dispatcher) {
            fruitDao.insert(fruitEntity)
        }
    }

}