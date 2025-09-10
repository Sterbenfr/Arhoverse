package com.example.arhoverse.presentation.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UserListScreen(
    viewModel: UserListViewModel,
    onUserClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val users by viewModel.users.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Utilisateurs",
            fontWeight = FontWeight.Bold,
            fontSize = androidx.compose.ui.unit.TextUnit.Unspecified,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (error != null) {
            Text(text = error!!, color = Color.Red)
        } else if (users.isEmpty()) {
            Text(text = "Aucun utilisateur.", color = Color.Gray)
        } else {
            LazyColumn {
                items(users) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { user.id.let(onUserClick) },
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {
                            AsyncImage(
                                model = user.avatarUrl,
                                contentDescription = "Avatar de l'utilisateur",
                                modifier = Modifier.height(48.dp).width(48.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = user.fullName ?: "", fontWeight = FontWeight.Bold)
                                Text(text = "@${user.username}", color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

