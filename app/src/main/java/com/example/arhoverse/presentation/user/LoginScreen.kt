package com.example.arhoverse.presentation.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    val loginState by authViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.login(username)
                if (authViewModel.isLoggedIn()) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        when (loginState) {
            is AuthViewModel.LoginState.Error -> {
                Text(
                    text = (loginState as AuthViewModel.LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            is AuthViewModel.LoginState.Success -> {
                Text(
                    text = "Login successful!",
                    color = MaterialTheme.colorScheme.primary
                )
            }
            else -> {}
        }
    }
}
