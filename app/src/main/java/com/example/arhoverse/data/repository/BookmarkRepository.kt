package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.domain.model.Bookmark

class BookmarkRepository(private val apiService: ApiService) {
    suspend fun getUserBookmarks(userId: Int): List<Bookmark> = apiService.getUserBookmarks(userId)
}

