package com.example.arhoverse.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.arhoverse.domain.usecase.GetStoriesUseCase

class StoryViewModelFactory(
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(getStoriesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}