package com.glendito.fruisca.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE

interface UserPreferences {
    suspend fun setEmail(email: String)
    suspend fun setRole(role: String)
    fun getEmail(): String
    fun getRole(): String
    fun reset()
}

class UserPreferencesImpl(appContext: Context): UserPreferences {

    private val sharedPreferences = appContext.getSharedPreferences("fruisca", MODE_PRIVATE)

    override suspend fun setEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    override suspend fun setRole(role: String) {
        val editor = sharedPreferences.edit()
        editor.putString("role", role)
        editor.apply()
    }

    override fun getEmail(): String {
        return sharedPreferences.getString("email", "").orEmpty()
    }

    override fun getRole(): String {
        return sharedPreferences.getString("role", "").orEmpty()
    }

    override fun reset() {
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("role")
        editor.apply()
    }
}