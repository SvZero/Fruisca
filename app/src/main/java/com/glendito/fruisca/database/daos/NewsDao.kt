package com.glendito.fruisca.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.glendito.fruisca.database.entities.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT EXISTS(SELECT * FROM news)")
    fun isExists(): Boolean

    @Insert
    fun insert(newsEntity: NewsEntity)

    @Query("SELECT * FROM news")
    fun getAll(): List<NewsEntity>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getDetail(id: Int): NewsEntity?
}