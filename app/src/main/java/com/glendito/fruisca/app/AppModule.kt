package com.glendito.fruisca.app

import android.content.Context
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.preferences.UserPreferencesImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    fun provideContext(): Context = applicationContext

    @Provides
    fun provideUserPreferences(): UserPreferences {
        return UserPreferencesImpl(applicationContext)
    }
}