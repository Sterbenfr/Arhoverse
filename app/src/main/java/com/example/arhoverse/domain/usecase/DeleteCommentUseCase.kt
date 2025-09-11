package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FeedRepository

class DeleteCommentUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(commentId: Int) = repository.deleteComment(commentId)
}

