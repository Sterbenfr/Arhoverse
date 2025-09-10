package com.example.arhoverse.data.repository

import com.example.arhoverse.domain.model.User

class UserRepository {

    private val users = listOf(
        User(1, "John Doe", "johndoe", "Bio de John"),
        User(2, "Jane Doe", "janedoe", "Bio de Jane"),
        User(3, "Alice Smith", "alicesmith", "Bio de Alice")
    )

    fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    fun getUsers(): List<User> {
        return users
    }
}
