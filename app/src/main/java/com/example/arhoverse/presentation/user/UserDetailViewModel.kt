package com.example.arhoverse.presentation.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arhoverse.domain.model.User
import com.example.arhoverse.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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
        }
    }
}
