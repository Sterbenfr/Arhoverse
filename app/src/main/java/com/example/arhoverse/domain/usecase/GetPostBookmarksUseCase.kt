package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository
import com.example.arhoverse.domain.model.Bookmark

class GetPostBookmarksUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: Int): List<Bookmark> = repository.getPostBookmarks(postId)
}

