package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.FollowRepository

class UnfollowUserUseCase(private val repository: FollowRepository) {
    suspend operator fun invoke(followId: Int) = repository.unfollowUser(followId)
}

