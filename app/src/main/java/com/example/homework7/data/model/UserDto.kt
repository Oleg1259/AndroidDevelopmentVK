package com.example.homework7.data.model

// Data Transfer Object (DTO) — структура данных, которая приходит с сервера или из БД
data class UserDto(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val contactsEmail: String
)