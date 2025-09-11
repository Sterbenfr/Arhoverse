package com.example.arhoverse.presentation.feed.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arhoverse.presentation.feed.FeedViewModel

@Composable
fun FeedScreen(viewModel: FeedViewModel, onPostClick: (String) -> Unit) {
    val posts by viewModel.feedPosts.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showCommentField = remember { mutableStateOf<String?>(null) }
    val commentText = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        if (posts.isEmpty() && errorMessage.isEmpty()){
            viewModel.loadFeed()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading && posts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(16.dp)
                )
            }
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(posts) { post ->
                    Column {
                        PostItem(
                            post = post,
                            onPostClick = onPostClick,
                            onLikeClick = { viewModel.toggleLike(it) },
                            onBookmarkClick = { viewModel.toggleBookmark(it) },
                            onCommentClick = { showCommentField.value = it.post.id.toString() }
                        )
                        if (showCommentField.value == post.post.id.toString()) {
                            OutlinedTextField(
                                value = commentText.value,
                                onValueChange = { commentText.value = it },
                                label = { Text("Ajouter un commentaire") },
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                            )
                            Button(
                                onClick = {
                                    viewModel.addComment(post.post.id, FeedViewModel.CURRENT_USER_ID, commentText.value)
                                    commentText.value = ""
                                    showCommentField.value = null
                                },
                                modifier = Modifier.align(Alignment.End).padding(8.dp)
                            ) {
                                Text("Envoyer")
                            }
                        }
                    }
                }
            }
        }
    }
}
