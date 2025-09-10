package com.example.arhoverse.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        viewModelScope.launch {
            try {
                val userResult = getUserUseCase(id)
                _user.value = userResult
                _error.value = null
            } catch (e: Exception) {
                _user.value = null
                _error.value = "Erreur: ${e.message}"
            }
        }
    }
}

class UserDetailViewModelFactory(
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailViewModel(getUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
