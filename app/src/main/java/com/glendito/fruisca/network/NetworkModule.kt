package com.glendito.fruisca.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://upload-b5b78.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideFruiscaService(retrofit: Retrofit): FruiscaService {
        return retrofit.create(FruiscaService::class.java)
    }
}