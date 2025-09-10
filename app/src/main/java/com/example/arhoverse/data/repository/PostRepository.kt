package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.domain.model.Post

class PostRepository(private val apiService: ApiService) {
    suspend fun getUserPosts(userId: Int): List<Post> = apiService.getUserPosts(userId)
}

