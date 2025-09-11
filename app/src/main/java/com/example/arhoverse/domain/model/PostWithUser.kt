package com.example.arhoverse.domain.model

data class PostWithUser(
    val post: Post,
    val user: User,
    val likesCount: Int,
    val commentsCount: Int,
    val bookmarksCount: Int, // Correction : non nullable
    val currentUserLikeId: Int? = null,
    val currentUserBookmarkId: Int? = null // id du bookmark de l'utilisateur courant, null si pas bookmarké
)