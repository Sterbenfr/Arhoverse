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
import androidx.compose.foundation.clickable
import com.example.arhoverse.domain.model.User
import com.example.arhoverse.domain.model.Post
import kotlinx.coroutines.flow.collect

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserDetailScreen(
    userId: Int,
    viewModel: UserDetailViewModel,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    onPostClick: ((Int) -> Unit)? = null
) {
    val user: User? by viewModel.user.collectAsState()
    val error: String? by viewModel.error.collectAsState()
    val posts: List<Post> by viewModel.posts.collectAsState()
    val postsError: String? by viewModel.postsError.collectAsState()

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
                    Text(text = user?.fullName ?: "", fontWeight = FontWeight.Bold, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified, textAlign = TextAlign.Center)
                    Text(text = "@${user?.username}", color = Color.Gray, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = user?.bio ?: "", textAlign = TextAlign.Center)
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
                                        .fillMaxWidth()
                                        .clickable { onPostClick?.invoke(post.id) },
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
                                        AsyncImage(
                                            model = post.imageUrl,
                                            contentDescription = "Image du post",
                                            modifier = Modifier.height(180.dp)
                                        )
                                        if (!post.caption.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(text = post.caption ?: "", fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified, maxLines = 1)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    val currentUserFollowId by viewModel.currentUserFollowId.collectAsState()
                    androidx.compose.material3.Button(
                        onClick = { viewModel.toggleFollow(userId) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(text = if (currentUserFollowId == null) "Suivre" else "Ne plus suivre")
                    }
                }
                error != null -> Text(text = error!!, modifier = Modifier.padding(16.dp))
                else -> Text(text = "Chargement...", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
