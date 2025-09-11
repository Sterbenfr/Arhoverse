package com.example.arhoverse.presentation.feed.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.arhoverse.domain.model.Story
import com.example.arhoverse.presentation.feed.FeedViewModel
import com.example.arhoverse.presentation.feed.StoryViewModel

@Composable
fun FeedScreen(
    feedViewModel: FeedViewModel,
    storyViewModel: StoryViewModel,
    onPostClick: (String) -> Unit
) {
    val posts by feedViewModel.feedPosts.collectAsState()
    val stories by storyViewModel.stories.collectAsState()
    val errorMessage by feedViewModel.errorMessage.collectAsState()
    val isLoading by feedViewModel.isLoading.collectAsState()

    val showCommentField = remember { mutableStateOf<String?>(null) }
    val commentText = remember { mutableStateOf("") }

    var currentStory by remember { mutableStateOf<Story?>(null) }

    LaunchedEffect(Unit) {
        if (posts.isEmpty() && errorMessage.isEmpty()) feedViewModel.loadFeed()
        if (stories.isEmpty()) storyViewModel.loadStories()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (isLoading && posts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, modifier = Modifier.padding(16.dp))
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    // 🔹 Stories en haut
                    item {
                        StoryRow(stories) { clickedStory ->
                            currentStory = clickedStory
                        }
                    }

                    // 🔹 Posts
                    items(posts) { post ->
                        Column {
                            PostItem(
                                post = post,
                                onPostClick = onPostClick,
                                onLikeClick = { feedViewModel.toggleLike(it) },
                                onBookmarkClick = { feedViewModel.toggleBookmark(it) },
                                onCommentClick = { showCommentField.value = it.post.id.toString() }
                            )

                            if (showCommentField.value == post.post.id.toString()) {
                                OutlinedTextField(
                                    value = commentText.value,
                                    onValueChange = { commentText.value = it },
                                    label = { Text("Ajouter un commentaire") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                                Button(
                                    onClick = {
                                        feedViewModel.addComment(
                                            post.post.id,
                                            FeedViewModel.CURRENT_USER_ID,
                                            commentText.value
                                        )
                                        commentText.value = ""
                                        showCommentField.value = null
                                    },
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(8.dp)
                                ) {
                                    Text("Envoyer")
                                }
                            }
                        }
                    }
                }
            }
        }

        // 🔹 Affichage plein écran de la story sélectionnée
        if (currentStory != null) {
            StoryScreen(story = currentStory!!, onClose = { currentStory = null })
        }
    }
}