package com.example.arhoverse.presentation.feed.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.arhoverse.domain.model.Story

@Composable
fun StoryRow(stories: List<Story>, onStoryClick: (Story) -> Unit) {
    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        stories.forEach { story ->
            Surface(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .clickable { onStoryClick(story) }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(story.mediaUrl),
                    contentDescription = "Story",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
