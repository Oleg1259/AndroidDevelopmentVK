package com.example.homework9.domain.repository

import com.example.homework9.domain.model.AppDetails
import com.example.homework9.domain.model.AppItem

interface CatalogRepository {
    suspend fun getCatalog(): Result<List<AppItem>>
    suspend fun getAppDetails(id: String): Result<AppDetails>
}