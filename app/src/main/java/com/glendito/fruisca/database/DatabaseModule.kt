package com.glendito.fruisca.database

import android.content.Context
import androidx.room.Room
import com.glendito.fruisca.database.daos.FruitDao
import com.glendito.fruisca.database.daos.NewsDao
import com.glendito.fruisca.database.daos.UserDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "fruisca").build()
    }

    @Provides
    fun provideFruitDao(appDatabase: AppDatabase): FruitDao = appDatabase.fruitDao()

    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao = appDatabase.newsDao()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
}