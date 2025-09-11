package com.example.arhoverse.domain.model

data class Follow(
    val id: Int,
    val followerId: Int,
    val followingId: Int,
    val createdAt: String
)

