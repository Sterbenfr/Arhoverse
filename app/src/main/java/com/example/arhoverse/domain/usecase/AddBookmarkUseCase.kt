package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository

class AddBookmarkUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: Int, userId: Int) = repository.addBookmark(postId, userId)
}

