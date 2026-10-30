package com.example.homework7.data.mapper

import com.example.homework7.data.model.UserDto
import com.example.homework7.domain.model.User

// Преобразователь (Mapper) из сетевой/локальной модели в бизнес-модель
object UserMapper {
    fun mapToDomain(dto: UserDto): User {
        return User(
            id = dto.userId,
            fullName = "${dto.firstName} ${dto.lastName}".trim(),
            email = dto.contactsEmail
        )
    }
}