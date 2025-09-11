package com.example.arhoverse.domain.usecase


import com.example.arhoverse.data.repository.StoryRepository
import com.example.arhoverse.domain.model.Story

class GetStoriesUseCase(private val repository: StoryRepository) {
    suspend operator fun invoke(): List<Story> {
        return repository.getStories()
    }
}
