package com.example.arhoverse.presentation.feed.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.arhoverse.domain.model.Story

@Composable
fun StoryScreen(story: Story, onClose: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable { onClose() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(story.mediaUrl),
            contentDescription = "Story",
            modifier = Modifier.fillMaxSize()
        )
    }
}
