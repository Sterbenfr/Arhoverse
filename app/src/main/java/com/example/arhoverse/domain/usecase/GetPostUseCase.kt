package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.PostRepository
import com.example.arhoverse.domain.model.Post

class GetPostUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postId: Int): Post = repository.getPost(postId)
}

