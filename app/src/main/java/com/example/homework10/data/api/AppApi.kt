package com.example.homework10.data.api

import com.example.homework10.data.model.AppDto
import kotlinx.coroutines.delay

interface AppApi {
    suspend fun getApps(): List<AppDto>

    suspend fun getRating(id: String): Float {
        delay(1500) // эмуляция сетевого запроса
        return (30..50).random() / 10f
    }
}