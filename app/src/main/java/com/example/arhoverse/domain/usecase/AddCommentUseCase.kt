package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository

class AddCommentUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: Int, userId: Int, content: String) = repository.addComment(postId, userId, content)
}

