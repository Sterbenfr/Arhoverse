package com.example.arhoverse.presentation.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arhoverse.domain.model.Post
import com.example.arhoverse.domain.model.User
import com.example.arhoverse.domain.usecase.GetUserPostsUseCase
import com.example.arhoverse.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getUserPostsUseCase: GetUserPostsUseCase
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _postsError = MutableStateFlow<String?>(null)
    val postsError: StateFlow<String?> = _postsError

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
        }
    }
}
