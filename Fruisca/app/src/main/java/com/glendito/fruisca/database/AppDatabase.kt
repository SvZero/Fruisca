package com.glendito.fruisca.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glendito.fruisca.database.daos.FruitDao
import com.glendito.fruisca.database.daos.NewsDao
import com.glendito.fruisca.database.daos.UserDao
import com.glendito.fruisca.database.entities.FruitEntity
import com.glendito.fruisca.database.entities.NewsEntity
import com.glendito.fruisca.database.entities.UserEntity

@Database(
    entities = [
        FruitEntity::class,
        NewsEntity::class,
        UserEntity::class
   ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fruitDao(): FruitDao
    abstract fun newsDao(): NewsDao
    abstract fun userDao(): UserDao
}