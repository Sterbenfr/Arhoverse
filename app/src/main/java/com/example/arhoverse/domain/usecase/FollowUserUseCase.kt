package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FollowRepository

class FollowUserUseCase(private val repository: FollowRepository) {
    suspend operator fun invoke(followerId: Int, followingId: Int) = repository.followUser(followerId, followingId)
}

