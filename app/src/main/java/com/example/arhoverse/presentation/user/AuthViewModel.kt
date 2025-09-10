package com.example.arhoverse.presentation.user

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private var loggedIn = false

    fun login(username: String) {
        if (username.isNotBlank()) {
            loggedIn = true
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Error("Username cannot be empty")
        }
    }

    fun isLoggedIn(): Boolean = loggedIn
}
