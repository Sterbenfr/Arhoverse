package com.example.arhoverse.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arhoverse.data.repository.FeedRepository
import com.example.arhoverse.domain.model.PostWithUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel (
    private val feedRepository : FeedRepository
): ViewModel() {
    private val _feedPosts = MutableStateFlow<List<PostWithUser>>(emptyList())
    val feedPosts: StateFlow<List<PostWithUser>> = _feedPosts

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun loadFeed(page: Int = 1, limit: Int = 10) {
        viewModelScope.launch {
            try {
                val posts = feedRepository.getFeed(page, limit)
                _feedPosts.value = posts
                _errorMessage.value = ""
            } catch (e: Exception) {
                _feedPosts.value = emptyList()
                _errorMessage.value = "Impossible de charger le feed. Vérifiez votre connexion."
            }
        }
    }
}