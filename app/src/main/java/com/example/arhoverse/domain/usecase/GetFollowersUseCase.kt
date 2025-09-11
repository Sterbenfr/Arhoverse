package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FollowRepository

class GetFollowersUseCase(private val repository: FollowRepository) {
    suspend operator fun invoke(userId: Int) = repository.getFollowers(userId)
}

