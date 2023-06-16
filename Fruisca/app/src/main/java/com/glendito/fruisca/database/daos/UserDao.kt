package com.glendito.fruisca.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glendito.fruisca.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity): Long

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email AND password = :password)")
    fun isExists(email: String, password: String): Boolean

    @Query("UPDATE users SET role = :role WHERE email = :email")
    fun updateRole(role: String, email: String)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUser(email: String): UserEntity?

    @Query("SELECT role FROM users WHERE email = :email")
    fun getRole(email: String): String

    @Query("""
        UPDATE users
        SET phone = :phone, address = :address, name = :name
        WHERE email = :email
    """)
    fun updateProfile(name: String, phone: String, address: String, email: String)
}