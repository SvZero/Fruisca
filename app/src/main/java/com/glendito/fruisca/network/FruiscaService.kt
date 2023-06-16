package com.glendito.fruisca.network

import retrofit2.Response
import retrofit2.http.GET

interface FruiscaService {
    @GET("register")
    suspend fun register(): Response<Unit>
}