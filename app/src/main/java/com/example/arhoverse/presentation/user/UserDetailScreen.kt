package com.example.arhoverse.presentation.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text

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

    when {
        user != null -> Text(
            text = "Nom complet : ${user!!.fullName}\nPseudo : ${user!!.username}\nBio : ${user!!.bio}",
            modifier = modifier
        )
        error != null -> Text(text = error!!, modifier = modifier)
        else -> Text(text = "Chargement...", modifier = modifier)
    }
}
