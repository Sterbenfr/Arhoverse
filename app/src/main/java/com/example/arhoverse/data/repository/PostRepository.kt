package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.domain.model.Post
import com.example.arhoverse.domain.model.Comment
import com.example.arhoverse.domain.model.Like

class PostRepository(private val apiService: ApiService) {
    suspend fun getUserPosts(userId: Int): List<Post> = apiService.getUserPosts(userId)
    suspend fun getPost(postId: Int): Post = apiService.getPost(postId)
    suspend fun getPostComments(postId: Int): List<Comment> = apiService.getPostComments(postId)
    suspend fun getPostLikes(postId: Int): List<Like> = apiService.getPostLikes(postId)
}
