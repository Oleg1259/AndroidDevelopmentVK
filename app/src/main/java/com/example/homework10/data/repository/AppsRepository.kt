package com.example.homework10.data.repository

import com.example.homework10.data.api.AppApi
import com.example.homework10.data.model.AppDto
import com.example.homework10.domain.model.AppInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppsRepository @Inject constructor(
    private val api: AppApi
) {
    suspend fun getApps(): List<AppInfo> {
        return api.getApps().map { it.toDomain() }
    }

    suspend fun getAppById(id: String): AppInfo? {
        return api.getApps().find { it.id == id }?.toDomain()
    }

    // Задание 2: Последовательная загрузка рейтингов
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()
        for (id in ids) {
            ratings[id] = api.getRating(id)
        }
        return ratings
    }

    // Задание 2: Параллельная загрузка рейтингов без ограничений
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async {
                id to api.getRating(id)
            }
        }.awaitAll().toMap()
    }

    private fun AppDto.toDomain() = AppInfo(
        id = id,
        name = name,
        developer = developer,
        category = category,
        description = description,
        sizeMb = sizeMb
    )
}