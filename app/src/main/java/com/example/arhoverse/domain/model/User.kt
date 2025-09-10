package com.example.arhoverse.domain.model

data class User(
    val id: Int,
    val username: String?,
    val fullName: String?,
    val avatarUrl: String?,
    val bio: String?
)
