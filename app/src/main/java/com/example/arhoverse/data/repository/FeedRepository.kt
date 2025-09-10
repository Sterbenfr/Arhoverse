package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.domain.model.PostWithUser
import kotlin.math.min

class FeedRepository(private val api: ApiService) {
    suspend fun getFeed(page: Int = 1, limit: Int = 10): List<PostWithUser> {
        val posts = api.getFeed()
        val feed = posts.map { post ->
            val user = api.getUser(post.userId)
            val likes = api.getPostLikes(post.id)
            val comments = api.getPostComments(post.id)
            PostWithUser(post, user, likes.size, comments.size)
        }
        val fromIndex = (page - 1) * limit
        val toIndex = min(fromIndex + limit, feed.size)
        return if (fromIndex < feed.size) feed.subList(fromIndex, toIndex) else emptyList()
    }
}