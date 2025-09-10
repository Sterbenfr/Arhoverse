package com.example.arhoverse.domain.model

data class Bookmark(
    val id: Int,
    val userId: Int,
    val postId: Int,
    val createdAt: String?
)

