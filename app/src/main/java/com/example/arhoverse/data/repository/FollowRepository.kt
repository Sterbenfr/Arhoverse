package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.data.remote.FollowRequest
import com.example.arhoverse.domain.model.Follow
import java.time.Instant

class FollowRepository(private val apiService: ApiService) {
    suspend fun getFollowers(userId: Int): List<Follow> = apiService.getFollowers(userId)
    suspend fun getFollowing(userId: Int): List<Follow> = apiService.getFollowing(userId)
    suspend fun followUser(followerId: Int, followingId: Int) = apiService.followUser(FollowRequest(followerId, followingId, Instant.now().toString()))
    suspend fun unfollowUser(followId: Int) = apiService.unfollowUser(followId)
}

