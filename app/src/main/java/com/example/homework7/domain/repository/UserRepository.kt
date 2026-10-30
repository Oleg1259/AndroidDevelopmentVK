package com.example.homework7.domain.repository

import package com.example.homework7.domain.model.User

// Интерфейс репозитория (контракт), который объявляет, КАКИЕ данные нужны приложению
interface UserRepository {
    suspend fun getUserProfile(): User
}