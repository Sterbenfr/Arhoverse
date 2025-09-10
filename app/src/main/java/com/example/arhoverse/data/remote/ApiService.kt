package com.example.arhoverse.data.remote

import com.example.arhoverse.domain.model.Post
import com.example.arhoverse.domain.model.User
import com.example.arhoverse.domain.model.Comment
import com.example.arhoverse.domain.model.Like
import com.example.arhoverse.domain.model.Bookmark
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @GET("users/{id}/posts")
    suspend fun getUserPosts(@Path("id") userId: Int): List<Post>

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("posts/{id}/comments")
    suspend fun getPostComments(@Path("id") postId: Int): List<Comment>

    @GET("posts/{id}/likes")
    suspend fun getPostLikes(@Path("id") postId: Int): List<Like>

    @GET("bookmarks")
    suspend fun getUserBookmarks(@Query("userId") userId: Int): List<Bookmark>
}
