package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.UserRepository
import com.example.arhoverse.domain.model.User

class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id: Int): User = repository.getUser(id)
}

