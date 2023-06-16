package com.glendito.fruisca.repositories

import com.glendito.fruisca.database.daos.FruitDao
import com.glendito.fruisca.database.daos.NewsDao
import com.glendito.fruisca.database.daos.UserDao
import com.glendito.fruisca.network.FruiscaService
import com.glendito.fruisca.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class RepositoryModule {

    @Provides
    fun providesUserRepository(
        userDao: UserDao,
        userPreferences: UserPreferences,
        fruiscaService: FruiscaService
    ): UserRepository {
        return UserRepositoryImpl(
            userDao,
            fruiscaService,
            userPreferences,
            Dispatchers.IO
        )
    }

    @Provides
    fun providesNewsRepository(newsDao: NewsDao): NewsRepository {
        return NewsRepositoryImpl(newsDao, Dispatchers.IO)
    }

    @Provides
    fun provideFruitRepository(fruitDao: FruitDao): FruitRepository {
        return FruitRepositoryImpl(fruitDao, Dispatchers.IO)
    }
}