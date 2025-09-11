package com.example.arhoverse.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.arhoverse.data.repository.FeedRepository
import com.example.arhoverse.domain.usecase.AddBookmarkUseCase
import com.example.arhoverse.domain.usecase.AddCommentUseCase
import com.example.arhoverse.domain.usecase.DeleteCommentUseCase
import com.example.arhoverse.domain.usecase.LikePostUseCase
import com.example.arhoverse.domain.usecase.RemoveBookmarkUseCase
import com.example.arhoverse.domain.usecase.UnlikePostUseCase

class FeedViewModelFactory(
    private val repository: FeedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            val likePostUseCase = LikePostUseCase(repository)
            val unlikePostUseCase = UnlikePostUseCase(repository)
            val addBookmarkUseCase = AddBookmarkUseCase(repository)
            val removeBookmarkUseCase = RemoveBookmarkUseCase(repository)
            val addCommentUseCase = AddCommentUseCase(repository)
            val deleteCommentUseCase = DeleteCommentUseCase(repository)
            @Suppress("UNCHECKED_CAST")
            return FeedViewModel(
                repository,
                likePostUseCase,
                unlikePostUseCase,
                addBookmarkUseCase,
                removeBookmarkUseCase,
                addCommentUseCase,
                deleteCommentUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
