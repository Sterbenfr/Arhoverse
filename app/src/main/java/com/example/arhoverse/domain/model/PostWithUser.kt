package com.example.arhoverse.domain.model

data class PostWithUser(
    val post: Post,
    val user: User,
    val likesCount: Int,
    val commentsCount: Int
)