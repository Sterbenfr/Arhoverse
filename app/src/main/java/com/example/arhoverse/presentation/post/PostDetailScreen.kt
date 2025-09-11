package com.example.arhoverse.presentation.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.arhoverse.domain.model.Comment
import com.example.arhoverse.domain.model.Like
import com.example.arhoverse.domain.model.Bookmark
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PostDetailScreen(
    postId: Int,
    viewModel: PostDetailViewModel,
    modifier: Modifier = Modifier,
    currentUserId: Int? = null,
    onBack: (() -> Unit)? = null
) {
    val post by viewModel.post.collectAsState()
    val author by viewModel.author.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val likes by viewModel.likes.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadPost(postId, currentUserId)
    }

    val commentAuthors = comments.map { comment ->
        author?.let { if (comment.userId == it.id) it else null }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Post") },
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
            if (error != null) {
                Text(text = error!!, color = Color.Red, modifier = Modifier.padding(16.dp))
            } else if (post == null) {
                Text(text = "Chargement...", modifier = Modifier.padding(16.dp))
            } else {
                Column(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = post!!.imageUrl,
                        contentDescription = "Image du post",
                        modifier = Modifier.height(220.dp).fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = post!!.caption ?: "", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (!post!!.hashtags.isNullOrEmpty()) {
                        Row {
                            post!!.hashtags!!.forEach { tag ->
                                AssistChip(
                                    onClick = {},
                                    label = { Text(text = "#$tag", color = Color(0xFF1976D2)) },
                                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFE3F2FD))
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    if (author != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = author!!.avatarUrl,
                                contentDescription = "Avatar auteur",
                                modifier = Modifier.height(40.dp).width(40.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = author!!.fullName ?: "", fontWeight = FontWeight.Medium)
                                Text(text = "@${author!!.username}", color = Color.Gray)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(text = "❤️ ${likes.size}", modifier = Modifier.padding(end = 16.dp))
                        Text(text = "🔖 ${bookmarks.count { it.postId == post!!.id }}", modifier = Modifier.padding(end = 16.dp))
                        Text(text = "💬 ${comments.size}")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Commentaires :", fontWeight = FontWeight.Bold)
                    if (comments.isEmpty()) {
                        Text(text = "Aucun commentaire.", color = Color.Gray)
                    } else {
                        LazyColumn {
                            items(comments.zip(commentAuthors)) { (comment, author) ->
                                Card(
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (author != null) {
                                                AsyncImage(
                                                    model = author.avatarUrl,
                                                    contentDescription = "Avatar du commentateur",
                                                    modifier = Modifier.height(24.dp).width(24.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(text = author.fullName ?: "", fontWeight = FontWeight.Bold)
                                                Text(text = "@${author.username}", color = Color.Gray, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified)
                                            } else {
                                                Text(text = "Utilisateur inconnu", color = Color.Gray)
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = comment.content ?: "", fontWeight = FontWeight.Medium)
                                        Text(text = "${comment.createdAt ?: "-"}", color = Color.Gray, fontSize = androidx.compose.ui.unit.TextUnit.Unspecified)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
