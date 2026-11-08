package com.example.homework9.domain.usecase

import com.example.homework9.domain.model.AppItem
import com.example.homework9.domain.repository.CatalogRepository

class GetCatalogUseCase(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(): Result<List<AppItem>> {
        return repository.getCatalog()
    }
}