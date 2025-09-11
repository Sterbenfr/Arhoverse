package com.example.arhoverse.data.repository

import com.example.arhoverse.data.remote.ApiService
import com.example.arhoverse.data.remote.LikeRequest
import com.example.arhoverse.data.remote.BookmarkRequest
import com.example.arhoverse.data.remote.CommentRequest
import com.example.arhoverse.domain.model.PostWithUser
import java.time.Instant
import kotlin.math.min

class FeedRepository(private val api: ApiService) {
    suspend fun getFeed(page: Int = 1, limit: Int = 10): List<PostWithUser> {
        val posts = api.getFeed()
        val feed = posts.map { post ->
            val user = api.getUser(post.userId)
            val likes = api.getPostLikes(post.id)
            val comments = api.getPostComments(post.id)
            val currentUserLikeId = likes.find { it.userId == com.example.arhoverse.presentation.feed.FeedViewModel.CURRENT_USER_ID }?.id
            val bookmarks = api.getUserBookmarks(com.example.arhoverse.presentation.feed.FeedViewModel.CURRENT_USER_ID)
            val bookmarksCount = bookmarks.size
            val currentUserBookmarkId = bookmarks.find { it.postId == post.id }?.id
            PostWithUser(post, user, likes.size, comments.size, bookmarksCount, currentUserLikeId, currentUserBookmarkId)
        }
        val fromIndex = (page - 1) * limit
        val toIndex = min(fromIndex + limit, feed.size)
        return if (fromIndex < feed.size) feed.subList(fromIndex, toIndex) else emptyList()
    }

    suspend fun likePost(postId: Int, userId: Int) = api.likePost(LikeRequest(postId, userId))
    suspend fun unlikePost(likeId: Int) = api.unlikePost(likeId)
    suspend fun addBookmark(postId: Int, userId: Int) = api.addBookmark(BookmarkRequest(userId, postId, Instant.now().toString()))
    suspend fun removeBookmark(bookmarkId: Int) = api.removeBookmark(bookmarkId)
    suspend fun addComment(postId: Int, userId: Int, content: String) = api.addComment(CommentRequest(postId, userId, content, Instant.now().toString()))
    suspend fun deleteComment(commentId: Int) = api.deleteComment(commentId)
    suspend fun getPostBookmarks(postId: Int): List<com.example.arhoverse.domain.model.Bookmark> = api.getPostBookmarks(postId)
}