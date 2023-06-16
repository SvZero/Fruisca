package com.glendito.fruisca.repositories

import com.glendito.fruisca.database.daos.UserDao
import com.glendito.fruisca.database.entities.UserEntity
import com.glendito.fruisca.network.FruiscaService
import com.glendito.fruisca.preferences.UserPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface UserRepository {
    suspend fun register(userEntity: UserEntity): Boolean
    suspend fun login(email: String, password: String): Boolean
    suspend fun setRole(role: String, email: String)
    suspend fun getUser(email: String): UserEntity?
    suspend fun getRole(email: String): String
    suspend fun editProfile(
        name: String,
        phone: String,
        address: String
    )
}

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val fruiscaService: FruiscaService,
    private val userPreferences: UserPreferences,
    private val dispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun register(userEntity: UserEntity): Boolean {
        return withContext(dispatcher) {
            userDao.insert(userEntity) > -1
            // Uncomment to use API
            /*val response = fruiscaService.register()
            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }*/
        }
    }

    override suspend fun editProfile(
        name: String,
        phone: String,
        address: String
    ) {
        withContext(dispatcher) {
            val email = userPreferences.getEmail()
            userDao.updateProfile(name, phone, address, email)
        }
    }

    override suspend fun login(email: String, password: String): Boolean {
        return withContext(dispatcher) {
            userDao.isExists(email, password)
        }
    }

    override suspend fun setRole(role: String, email: String) {
        withContext(dispatcher) {
            userDao.updateRole(role, email)
        }
    }

    override suspend fun getUser(email: String): UserEntity? {
        return withContext(dispatcher) {
            userDao.getUser(email)
        }
    }

    override suspend fun getRole(email: String): String {
        return withContext(dispatcher) {
            userDao.getRole(email)
        }
    }
}