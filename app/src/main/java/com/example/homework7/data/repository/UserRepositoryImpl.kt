package com.example.homework7.data.repository

import com.example.homework7.data.mapper.UserMapper
import com.example.homework7.data.model.UserDto
import com.example.homework7.domain.model.User
import com.example.homework7.domain.repository.UserRepository
import kotlinx.coroutines.delay

// Реализация репозитория. Хранит хардкод и управляет источниками данных
class UserRepositoryImpl : UserRepository {

    override suspend fun getUserProfile(): User {
        // Имитируем задержку ответа сервера (1.5 секунды)
        delay(1500)

        // Хардкод данные в формате DTO
        val hardcodedDto = UserDto(
            userId = "999",
            firstName = "Алексей",
            lastName = "Петров",
            contactsEmail = "alex.petrov@email.com"
        )

        // Конвертируем DTO в Domain модель через Маппер и отдаем наружу
        return UserMapper.mapToDomain(hardcodedDto)
    }
}