package com.glendito.fruisca.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.glendito.fruisca.database.entities.FruitEntity

@Dao
interface FruitDao {
    @Query("SELECT * FROM fruits")
    fun getAll(): List<FruitEntity>

    @Insert
    fun insert(fruitEntity: FruitEntity)
}