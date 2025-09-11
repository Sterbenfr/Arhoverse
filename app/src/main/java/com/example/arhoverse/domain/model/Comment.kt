package com.example.arhoverse.domain.model

data class Comment(
    val id: Int,
    val postId: Int,
    val userId: Int,
    val content: String?,
    val createdAt: String?
)

