package com.example.arhoverse.presentation.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation

data class TestUser(val fullName: String, val username: String, val password: String)

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }
    val users = listOf(
        TestUser("Alice Martin", "alice", "alice123"),
        TestUser("Bob Dupont", "bob", "bob123"),
        TestUser("Charlie Durand", "charlie", "charlie123")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Connexion",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'utilisateur") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        if (loginError.isNotEmpty()) {
            Text(
                text = loginError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val user = users.find { it.username == username && it.password == password }
                if (user != null) {
                    loginError = ""
                    onLoginSuccess(user.username)
                } else {
                    loginError = "Nom d'utilisateur ou mot de passe incorrect"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Se connecter")
        }
    }
}
