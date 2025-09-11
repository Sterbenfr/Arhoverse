package com.example.arhoverse.presentation.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arhoverse.domain.usecase.GetFollowersUseCase
import com.example.arhoverse.domain.usecase.FollowUserUseCase
import com.example.arhoverse.domain.usecase.GetUserPostsUseCase
import com.example.arhoverse.domain.usecase.GetUserUseCase
import com.example.arhoverse.domain.usecase.UnfollowUserUseCase
import com.example.arhoverse.domain.model.User
import com.example.arhoverse.domain.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _postsError = MutableStateFlow<String?>(null)
    val postsError: StateFlow<String?> = _postsError

    private val _currentUserFollowId = MutableStateFlow<Int?>(null)
    val currentUserFollowId: StateFlow<Int?> = _currentUserFollowId

    companion object {
        const val CURRENT_USER_ID = 1 // Simule l'utilisateur connecté
    }

    fun loadUser(id: Int) {
        Log.d("UserDetail", "Appel API pour l'utilisateur id=$id")
        viewModelScope.launch {
            try {
                val userResult = getUserUseCase(id)
                Log.d("UserDetail", "Réponse API: $userResult")
                _user.value = userResult
                _error.value = null
            } catch (e: Exception) {
                Log.e("UserDetail", "Erreur API: ${e.message}")
                _user.value = null
                _error.value = "Erreur de chargement : ${e.message}"
            }
            try {
                val postsResult = getUserPostsUseCase(id)
                Log.d("UserDetail", "Posts API: $postsResult")
                _posts.value = postsResult
                _postsError.value = null
            } catch (e: Exception) {
                Log.e("UserDetail", "Erreur API posts: ${e.message}")
                _posts.value = emptyList()
                _postsError.value = "Erreur de chargement des posts : ${e.message}"
            }
            try {
                val followers = getFollowersUseCase(id)
                val follow = followers.find { it.followerId == CURRENT_USER_ID }
                _currentUserFollowId.value = follow?.id
            } catch (e: Exception) {
                _currentUserFollowId.value = null
            }
        }
    }

    fun toggleFollow(profileUserId: Int) {
        viewModelScope.launch {
            try {
                if (_currentUserFollowId.value != null) {
                    unfollowUserUseCase(_currentUserFollowId.value!!)
                } else {
                    followUserUseCase(CURRENT_USER_ID, profileUserId)
                }
                loadUser(profileUserId)
            } catch (e: Exception) {
                _error.value = "Erreur lors du follow/unfollow : ${e.message}"
            }
        }
    }
}
