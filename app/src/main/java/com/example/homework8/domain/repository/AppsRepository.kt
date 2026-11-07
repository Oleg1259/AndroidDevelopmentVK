package com.example.homework8.domain.repository

import com.example.homework8.domain.model.AppItem

interface AppsRepository {
    suspend fun getApps(): List<AppItem>
}