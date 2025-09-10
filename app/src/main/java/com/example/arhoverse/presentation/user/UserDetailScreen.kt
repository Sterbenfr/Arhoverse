package com.example.arhoverse.presentation.user

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserDetailScreen(
    userId: Int,
    viewModel: UserDetailViewModel,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    val user by viewModel.user.collectAsState()
    val error by viewModel.error.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val postsError by viewModel.postsError.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = user?.fullName ?: "Détail utilisateur") },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                        }
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            when {
                user != null -> Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = user!!.avatarUrl,
                        contentDescription = "Avatar de l'utilisateur",
                        modifier = Modifier.height(120.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = user!!.fullName ?: "", fontWeight = FontWeight.Bold, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified, textAlign = TextAlign.Center)
                    Text(text = "@${user!!.username}", color = Color.Gray, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = user!!.bio ?: "", textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Posts :", fontWeight = FontWeight.Bold, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified)
                    if (postsError != null) {
                        Text(text = postsError!!, color = Color.Red)
                    } else if (posts.isEmpty()) {
                        Text(text = "Aucun post.", color = Color.Gray)
                    } else {
                        LazyColumn {
                            items(posts) { post ->
                                Card(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 4.dp)
                                        .fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
                                        AsyncImage(
                                            model = post.imageUrl,
                                            contentDescription = "Image du post",
                                            modifier = Modifier.height(180.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(text = post.caption ?: "", fontWeight = FontWeight.Medium)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        if (!post.hashtags.isNullOrEmpty()) {
                                            Row {
                                                post.hashtags!!.forEach { tag ->
                                                    AssistChip(
                                                        onClick = {},
                                                        label = { Text(text = "#$tag") },
                                                        colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFE0E0E0))
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Date : ${post.createdAt ?: "-"}", color = Color.Gray, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified)
                                    }
                                }
                            }
                        }
                    }
                }
                error != null -> Text(text = error!!, modifier = Modifier.padding(16.dp))
                else -> Text(text = "Chargement...", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
