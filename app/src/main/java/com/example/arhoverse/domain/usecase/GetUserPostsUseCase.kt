package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.PostRepository
import com.example.arhoverse.domain.model.Post

class GetUserPostsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(userId: Int): List<Post> = repository.getUserPosts(userId)
}

