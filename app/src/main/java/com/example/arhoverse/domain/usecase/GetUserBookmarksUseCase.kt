package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.BookmarkRepository
import com.example.arhoverse.domain.model.Bookmark

class GetUserBookmarksUseCase(private val repository: BookmarkRepository) {
    suspend operator fun invoke(userId: Int): List<Bookmark> = repository.getUserBookmarks(userId)
}

