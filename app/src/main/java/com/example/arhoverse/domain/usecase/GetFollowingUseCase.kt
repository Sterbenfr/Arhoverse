package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FollowRepository

class GetFollowingUseCase(private val repository: FollowRepository) {
    suspend operator fun invoke(userId: Int) = repository.getFollowing(userId)
}

