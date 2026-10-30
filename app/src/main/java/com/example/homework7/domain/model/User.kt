package com.example.homework7.domain.model

// Чистая доменная модель (Entity), с которой работает UI
data class User(
    val id: String,
    val fullName: String,
    val email: String
)