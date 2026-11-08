package com.example.homework9.data.repository

import com.example.homework9.data.mapper.toDomain
import com.example.homework9.data.remote.CatalogApi
import com.example.homework9.domain.model.AppDetails
import com.example.homework9.domain.model.AppItem
import com.example.homework9.domain.repository.CatalogRepository

class CatalogRepositoryImpl(
    private val api: CatalogApi
) : CatalogRepository {

    override suspend fun getCatalog(): Result<List<AppItem>> {
        return try {
            val dtoList = api.getCatalog()
            val domainList = dtoList.map { it.toDomain() }
            Result.success(domainList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAppDetails(id: String): Result<AppDetails> {
        return try {
            val dto = api.getAppById(id)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}