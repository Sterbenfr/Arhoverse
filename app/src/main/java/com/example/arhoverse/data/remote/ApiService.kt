package com.example.arhoverse.data.remote

import com.example.arhoverse.domain.model.Post
import com.example.arhoverse.domain.model.User
import com.example.arhoverse.domain.model.Comment
import com.example.arhoverse.domain.model.Like
import com.example.arhoverse.domain.model.Bookmark
import com.example.arhoverse.domain.model.Story
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.DELETE

data class LikeRequest(val postId: Int, val userId: Int)
data class BookmarkRequest(val userId: Int, val postId: Int, val createdAt: String)
data class CommentRequest(val postId: Int, val userId: Int, val content: String, val createdAt: String)
data class FollowRequest(val followerId: Int, val followingId: Int, val createdAt: String)

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


    @GET("feed")
    suspend fun getFeed(): List<Post>

    @GET("stories")
    suspend fun getStories(): List<Story>

    @GET("users/{id}/stories")
    suspend fun getUserStories(@Path("id") userId: Int): List<Story>



    @POST("likes")
    suspend fun likePost(@Body body: LikeRequest): Like

    @DELETE("likes/{id}")
    suspend fun unlikePost(@Path("id") likeId: Int)

    @POST("bookmarks")
    suspend fun addBookmark(@Body body: BookmarkRequest): Bookmark

    @DELETE("bookmarks/{id}")
    suspend fun removeBookmark(@Path("id") bookmarkId: Int)

    @POST("comments")
    suspend fun addComment(@Body body: CommentRequest): Comment

    @DELETE("comments/{id}")
    suspend fun deleteComment(@Path("id") commentId: Int)

    @GET("users/{id}/followers")
    suspend fun getFollowers(@Path("id") userId: Int): List<com.example.arhoverse.domain.model.Follow>

    @GET("users/{id}/following")
    suspend fun getFollowing(@Path("id") userId: Int): List<com.example.arhoverse.domain.model.Follow>

    @POST("follows")
    suspend fun followUser(@Body body: FollowRequest): com.example.arhoverse.domain.model.Follow

    @DELETE("follows/{id}")
    suspend fun unfollowUser(@Path("id") followId: Int)

    @GET("posts/{postId}/bookmarks")
    suspend fun getPostBookmarks(@Path("postId") postId: Int): List<Bookmark>
}
