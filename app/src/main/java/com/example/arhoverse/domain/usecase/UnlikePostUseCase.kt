package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository

class UnlikePostUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(likeId: Int) = repository.unlikePost(likeId)
}

