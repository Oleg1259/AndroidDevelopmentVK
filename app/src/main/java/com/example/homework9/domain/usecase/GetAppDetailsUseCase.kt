package com.example.homework9.domain.usecase

import com.example.homework9.domain.model.AppDetails
import com.example.homework9.domain.repository.CatalogRepository

class GetAppDetailsUseCase(
    private val repository: CatalogRepository
) {
    suspend operator fun invoke(id: String): Result<AppDetails> {
        return repository.getAppDetails(id)
    }
}