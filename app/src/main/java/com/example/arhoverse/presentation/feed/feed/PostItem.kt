package com.example.arhoverse.presentation.feed.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.arhoverse.domain.model.PostWithUser
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PostItem(
    post: PostWithUser,
    onPostClick: (String) -> Unit,
    onLikeClick: (PostWithUser) -> Unit,
    onBookmarkClick: (PostWithUser) -> Unit,
    onCommentClick: (PostWithUser) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPostClick(post.post.id.toString()) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(post.user.avatarUrl),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = post.user.username ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(post.post.imageUrl),
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.post.caption ?: "")
            Spacer(modifier = Modifier.height(2.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "\u2764\ufe0f ${post.likesCount}",
                    modifier = Modifier.clickable { onLikeClick(post) }
                )
                Text(
                    text = "\ud83d\udd16 ${post.bookmarksCount}", // bookmark icon + nombre
                    modifier = Modifier.clickable { onBookmarkClick(post) }
                )
                Text(
                    text = "\ud83d\udcac ${post.commentsCount}",
                    modifier = Modifier.clickable { onCommentClick(post) }
                )
            }
            Text(text = formatDate(post.post.createdAt ?: ""))
        }
    }
}

private fun formatDate(dateStr: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = sdf.parse(dateStr)
        val output = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        output.format(date ?: Date())
    } catch (_: Exception) {
        dateStr
    }
}
