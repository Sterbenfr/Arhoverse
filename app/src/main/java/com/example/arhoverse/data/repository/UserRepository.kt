package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.domain.model.User

class UserRepository(private val apiService: ApiService) {
    suspend fun getUser(id: Int): User = apiService.getUser(id)
    suspend fun getUsers(): List<User> = apiService.getUsers()
}
