package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.PostRepository
import com.example.arhoverse.domain.model.Comment

class GetPostCommentsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postId: Int): List<Comment> = repository.getPostComments(postId)
}

