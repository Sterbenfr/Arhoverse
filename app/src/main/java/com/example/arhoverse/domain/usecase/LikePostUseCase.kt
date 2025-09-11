package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository

class LikePostUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: Int, userId: Int) = repository.likePost(postId, userId)
}

