package com.example.arhoverse.presentation.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arhoverse.domain.model.Post
import com.example.arhoverse.domain.model.User
import com.example.arhoverse.domain.model.Comment
import com.example.arhoverse.domain.model.Like
import com.example.arhoverse.domain.model.Bookmark
import com.example.arhoverse.domain.usecase.GetPostUseCase
import com.example.arhoverse.domain.usecase.GetUserUseCase
import com.example.arhoverse.domain.usecase.GetPostCommentsUseCase
import com.example.arhoverse.domain.usecase.GetPostLikesUseCase
import com.example.arhoverse.domain.usecase.GetUserBookmarksUseCase
import com.example.arhoverse.domain.usecase.LikePostUseCase
import com.example.arhoverse.domain.usecase.UnlikePostUseCase
import com.example.arhoverse.domain.usecase.AddBookmarkUseCase
import com.example.arhoverse.domain.usecase.RemoveBookmarkUseCase
import java.time.Instant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val getPostLikesUseCase: GetPostLikesUseCase,
    private val getUserBookmarksUseCase: GetUserBookmarksUseCase?,
    private val likePostUseCase: LikePostUseCase,
    private val unlikePostUseCase: UnlikePostUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val getPostBookmarksUseCase: com.example.arhoverse.domain.usecase.GetPostBookmarksUseCase,
    private val addCommentUseCase: com.example.arhoverse.domain.usecase.AddCommentUseCase // Ajouté
) : ViewModel() {
    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _author = MutableStateFlow<User?>(null)
    val author: StateFlow<User?> = _author

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    private val _likes = MutableStateFlow<List<Like>>(emptyList())
    val likes: StateFlow<List<Like>> = _likes

    private val _bookmarks = MutableStateFlow<List<Bookmark>>(emptyList())
    val bookmarks: StateFlow<List<Bookmark>> = _bookmarks

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _postBookmarksCount = MutableStateFlow(0)
    val postBookmarksCount: StateFlow<Int> = _postBookmarksCount

    fun loadPost(postId: Int, currentUserId: Int? = null) {
        viewModelScope.launch {
            try {
                val postResult = getPostUseCase(postId)
                _post.value = postResult
                _author.value = getUserUseCase(postResult.userId)
                val commentsRaw = getPostCommentsUseCase(postId)
                val commentsEnriched = commentsRaw.map { comment ->
                    val user = getUserUseCase(comment.userId)
                    if (user == null) {
                        println("Utilisateur non trouvé pour userId: ${comment.userId}")
                    }
                    comment.copy(userName = user?.username)
                }
                _comments.value = commentsEnriched
                _likes.value = getPostLikesUseCase(postId)
                if (getUserBookmarksUseCase != null && currentUserId != null) {
                    _bookmarks.value = getUserBookmarksUseCase(currentUserId)
                }
                // Ajout récupération du nombre total de bookmarks du post
                _postBookmarksCount.value = getPostBookmarksUseCase(postId).size
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erreur de chargement : ${e.message}"
            }
        }
    }

    fun likePost(postId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                val like = likePostUseCase(postId, userId)
                _likes.value = _likes.value + like
                loadPost(postId, userId)
            } catch (e: Exception) {
                _error.value = "Erreur lors du like : ${e.message}"
            }
        }
    }

    fun unlikePost(likeId: Int) {
        viewModelScope.launch {
            try {
                unlikePostUseCase(likeId)
                _likes.value = _likes.value.filterNot { it.id == likeId }
                post.value?.let { loadPost(it.id, author.value?.id) }
            } catch (e: Exception) {
                _error.value = "Erreur lors du unlike : ${e.message}"
            }
        }
    }

    fun bookmarkPost(postId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                val bookmark = addBookmarkUseCase(postId, userId)
                _bookmarks.value = _bookmarks.value + bookmark
                loadPost(postId, userId)
            } catch (e: Exception) {
                _error.value = "Erreur lors du bookmark : ${e.message}"
            }
        }
    }

    fun unbookmarkPost(bookmarkId: Int) {
        viewModelScope.launch {
            try {
                removeBookmarkUseCase(bookmarkId)
                _bookmarks.value = _bookmarks.value.filterNot { it.id == bookmarkId }
                post.value?.let { loadPost(it.id, author.value?.id) }
            } catch (e: Exception) {
                _error.value = "Erreur lors du retrait du bookmark : ${e.message}"
            }
        }
    }

    fun toggleLike(currentUserId: Int) {
        val postId = post.value?.id ?: return
        val currentLike = likes.value.find { it.userId == currentUserId }
        Log.d("PostDetailViewModel", "toggleLike: postId=$postId, currentUserId=$currentUserId, currentLike=$currentLike")
        viewModelScope.launch {
            try {
                if (currentLike != null) {
                    unlikePostUseCase(currentLike.id)
                    _likes.value = _likes.value.filterNot { it.id == currentLike.id }
                    Log.d("PostDetailViewModel", "Like supprimé pour userId=$currentUserId")
                } else {
                    val like = likePostUseCase(postId, currentUserId)
                    _likes.value = _likes.value + like
                    Log.d("PostDetailViewModel", "Like ajouté pour userId=$currentUserId")
                }
            } catch (e: Exception) {
                _error.value = "Erreur lors du like/unlike : ${e.message}"
                Log.e("PostDetailViewModel", "Erreur lors du like/unlike : ${e.message}")
            }
        }
    }

    fun toggleBookmark(currentUserId: Int) {
        val postId = post.value?.id ?: return
        val currentBookmark = bookmarks.value.find { it.userId == currentUserId && it.postId == postId }
        viewModelScope.launch {
            try {
                if (currentBookmark != null) {
                    removeBookmarkUseCase(currentBookmark.id)
                    _bookmarks.value = _bookmarks.value.filterNot { it.id == currentBookmark.id }
                } else {
                    val bookmark = addBookmarkUseCase(postId, currentUserId)
                    _bookmarks.value = _bookmarks.value + bookmark
                }
            } catch (e: Exception) {
                _error.value = "Erreur lors du bookmark/unbookmark : ${e.message}"
            }
        }
    }

    fun addComment(postId: Int, userId: Int, content: String) {
        viewModelScope.launch {
            try {
                val comment = addCommentUseCase(postId, userId, content)
                val user = getUserUseCase(userId)
                val enrichedComment = comment.copy(userName = user?.username)
                _comments.value = _comments.value + enrichedComment
            } catch (e: Exception) {
                _error.value = "Erreur lors de l'ajout du commentaire : ${e.message}"
            }
        }
    }
}
