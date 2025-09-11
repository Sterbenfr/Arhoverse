package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.PostRepository
import com.example.arhoverse.domain.model.Like

class GetPostLikesUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postId: Int): List<Like> = repository.getPostLikes(postId)
}

