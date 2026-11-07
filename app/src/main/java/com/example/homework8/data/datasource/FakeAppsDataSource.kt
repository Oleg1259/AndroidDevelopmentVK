package com.example.homework8.data.datasource
import kotlinx.coroutines.delay
import javax.inject.Inject

// Имитация ответа от сервера или базы данных (DTO)
data class AppDto(val id: Int, val rawTitle: String)

class FakeAppsDataSource @Inject constructor() {
    suspend fun fetchApps(): List<AppDto> {
        delay(500) // Имитация сетевой задержки
        return listOf(
            AppDto(1, "telegram"),
            AppDto(2, "spotify"),
            AppDto(3, "github")
        )
    }
}