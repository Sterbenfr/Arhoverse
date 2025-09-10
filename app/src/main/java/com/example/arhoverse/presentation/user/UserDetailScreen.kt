package com.example.arhoverse.presentation.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserDetailScreen(
    userId: Int,
    viewModel: UserDetailViewModel,
    modifier: Modifier = Modifier
) {
    val user by viewModel.user.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            user != null -> {
                Text(text = "Nom complet : ${user!!.fullName}")
                Text(text = "Pseudo : ${user!!.username}")
                Text(text = "Bio : ${user!!.bio}")
            }
            error != null -> {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
            }
            else -> {
                Text(text = "Chargement...")
            }
        }
    }
}
