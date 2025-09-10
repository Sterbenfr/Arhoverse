package com.example.arhoverse.presentation.feed.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arhoverse.presentation.feed.FeedViewModel

@Composable
fun FeedScreen(viewModel: FeedViewModel) {
    val posts by viewModel.feedPosts.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    LaunchedEffect(Unit) {
        if (posts.isEmpty() && errorMessage.isEmpty()){
            viewModel.loadFeed()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
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
                PostItem(post)
            }
        }
    }
}
