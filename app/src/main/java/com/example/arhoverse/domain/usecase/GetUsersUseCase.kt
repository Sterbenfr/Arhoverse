package com.example.arhoverse.domain.usecase

import com.example.arhoverse.data.repository.UserRepository
import com.example.arhoverse.domain.model.User

class GetUsersUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): List<User> = repository.getUsers()
}

