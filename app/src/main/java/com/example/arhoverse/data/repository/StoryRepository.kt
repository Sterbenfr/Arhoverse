package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.domain.model.Story

class StoryRepository(private val apiService: ApiService) {
    suspend fun getStories(): List<Story> = apiService.getStories()
    suspend fun getUserStories(userId: Int): List<Story> = apiService.getUserStories(userId)
}
