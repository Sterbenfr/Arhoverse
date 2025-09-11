package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository

class RemoveBookmarkUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(bookmarkId: Int) = repository.removeBookmark(bookmarkId)
}

