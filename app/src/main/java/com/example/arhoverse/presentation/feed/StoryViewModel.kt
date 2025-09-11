package com.example.arhoverse.presentation.feed


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arhoverse.domain.model.Story
import com.example.arhoverse.domain.usecase.GetStoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoryViewModel(private val getStoriesUseCase: GetStoriesUseCase) : ViewModel() {

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> = _stories

    fun loadStories() {
        viewModelScope.launch {
            _stories.value = getStoriesUseCase()
        }
    }
}