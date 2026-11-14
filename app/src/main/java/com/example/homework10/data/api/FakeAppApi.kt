package com.example.homework10.data.api

import com.example.homework10.data.model.AppDto
import kotlinx.coroutines.delay

class FakeAppApi : AppApi {

    private val mockApps = List(12) { index ->
        val num = index + 1
        AppDto(
            id = "app_$num",
            name = "Приложение #$num",
            developer = "Studio $num",
            category = if (num % 2 == 0) "Инструменты" else "Игры",
            description = "Подробное описание функционала для Приложения #$num. Поддерживает оффлайн-режим и синхронизацию.",
            sizeMb = 25 + num * 4
        )
    }

    override suspend fun getApps(): List<AppDto> {
        delay(400)
        return mockApps
    }
}
