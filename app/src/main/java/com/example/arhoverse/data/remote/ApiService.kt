package com.example.arhoverse.data.remote

import com.example.arhoverse.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User
}

