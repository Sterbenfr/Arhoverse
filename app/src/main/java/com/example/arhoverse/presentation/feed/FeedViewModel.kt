package com.example.arhoverse.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arhoverse.data.repository.FeedRepository
import com.example.arhoverse.domain.model.PostWithUser
import com.example.arhoverse.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel (
    private val feedRepository : FeedRepository,
    private val likePostUseCase: LikePostUseCase,
    private val unlikePostUseCase: UnlikePostUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
): ViewModel() {
    private val _feedPosts = MutableStateFlow<List<PostWithUser>>(emptyList())
    val feedPosts: StateFlow<List<PostWithUser>> = _feedPosts

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    companion object {
        // Utilisateur connecté simulé (à remplacer par le vrai système plus tard)
        const val CURRENT_USER_ID = 1
    }

    fun loadFeed(page: Int = 1, limit: Int = 10) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val posts = feedRepository.getFeed(page, limit)
                _feedPosts.value = posts
                _errorMessage.value = ""
            } catch (e: Exception) {
                _feedPosts.value = emptyList()
                _errorMessage.value = "Impossible de charger le feed. Vérifiez votre connexion."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun likePost(postId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                likePostUseCase(postId, userId)
                loadFeed()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du like : ${e.message}"
            }
        }
    }
    fun unlikePost(likeId: Int) {
        viewModelScope.launch {
            try {
                unlikePostUseCase(likeId)
                loadFeed()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du unlike : ${e.message}"
            }
        }
    }
    fun addBookmark(postId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                addBookmarkUseCase(postId, userId)
                loadFeed()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du bookmark : ${e.message}"
            }
        }
    }
    fun removeBookmark(bookmarkId: Int) {
        viewModelScope.launch {
            try {
                removeBookmarkUseCase(bookmarkId)
                loadFeed()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du retrait du bookmark : ${e.message}"
            }
        }
    }
    fun addComment(postId: Int, userId: Int, content: String) {
        viewModelScope.launch {
            try {
                addCommentUseCase(postId, userId, content)
                loadFeed()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de l'ajout du commentaire : ${e.message}"
            }
        }
    }
    fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            try {
                deleteCommentUseCase(commentId)
                loadFeed()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la suppression du commentaire : ${e.message}"
            }
        }
    }
    fun toggleLike(post: PostWithUser) {
        viewModelScope.launch {
            try {
                if (post.currentUserLikeId != null) {
                    post.currentUserLikeId?.let { unlikePostUseCase(it) }
                    // Mise à jour locale
                    _feedPosts.value = _feedPosts.value.map {
                        if (it.post.id == post.post.id) it.copy(
                            likesCount = it.likesCount - 1,
                            currentUserLikeId = null
                        ) else it
                    }
                } else {
                    val like = likePostUseCase(post.post.id, CURRENT_USER_ID)
                    _feedPosts.value = _feedPosts.value.map {
                        if (it.post.id == post.post.id) it.copy(
                            likesCount = it.likesCount + 1,
                            currentUserLikeId = like.id
                        ) else it
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du like/unlike : ${e.message}"
            }
        }
    }
    fun toggleBookmark(post: PostWithUser) {
        viewModelScope.launch {
            try {
                if (post.currentUserBookmarkId != null) {
                    post.currentUserBookmarkId?.let { removeBookmarkUseCase(it) }
                    _feedPosts.value = _feedPosts.value.map {
                        if (it.post.id == post.post.id) it.copy(
                            bookmarksCount = it.bookmarksCount - 1,
                            currentUserBookmarkId = null
                        ) else it
                    }
                } else {
                    val bookmark = addBookmarkUseCase(post.post.id, CURRENT_USER_ID)
                    _feedPosts.value = _feedPosts.value.map {
                        if (it.post.id == post.post.id) it.copy(
                            bookmarksCount = it.bookmarksCount + 1,
                            currentUserBookmarkId = bookmark.id
                        ) else it
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du bookmark/unbookmark : ${e.message}"
            }
        }
    }
}