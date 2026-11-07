package com.example.homework8.domain.usecase
import com.example.homework8.domain.model.AppItem
import com.example.homework8.domain.repository.AppsRepository
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppsRepository
) {
    suspend operator fun invoke(): List<AppItem> {
        return repository.getApps()
    }
}