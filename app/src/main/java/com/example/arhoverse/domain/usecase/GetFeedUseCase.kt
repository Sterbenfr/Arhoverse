package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository
import com.example.arhoverse.domain.model.PostWithUser

class GetFeedUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 10): List<PostWithUser> =
        repository.getFeed(page, limit)
}

