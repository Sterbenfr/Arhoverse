package com.example.arhoverse.domain.model

data class Story(
    val id: Int,
    val userId: Int,
    val mediaUrl: String,
    val createdAt: String,
    val expiresAt: String
)
