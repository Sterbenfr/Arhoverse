package com.example.arhoverse.domain.model

data class Post(
    val id: Int,
    val userId: Int,
    val imageUrl: String?,
    val caption: String?,
    val hashtags: List<String>?,
    val taggedUserIds: List<Int>?,
    val createdAt: String?
)

