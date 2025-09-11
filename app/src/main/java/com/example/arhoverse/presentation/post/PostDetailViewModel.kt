package com.example.arhoverse.presentation.post

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val getPostLikesUseCase: GetPostLikesUseCase,
    private val getUserBookmarksUseCase: GetUserBookmarksUseCase?
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

    fun loadPost(postId: Int, currentUserId: Int? = null) {
        viewModelScope.launch {
            try {
                val postResult = getPostUseCase(postId)
                _post.value = postResult
                _author.value = getUserUseCase(postResult.userId)
                _comments.value = getPostCommentsUseCase(postId)
                _likes.value = getPostLikesUseCase(postId)
                if (getUserBookmarksUseCase != null && currentUserId != null) {
                    _bookmarks.value = getUserBookmarksUseCase(currentUserId)
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erreur de chargement : ${e.message}"
            }
        }
    }
}

