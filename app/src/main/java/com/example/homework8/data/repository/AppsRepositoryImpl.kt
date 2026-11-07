package com.example.homework8.data.repository
import com.example.homework8.data.datasource.FakeAppsDataSource
import com.example.homework8.data.mapper.AppsMapper
import com.example.homework8.domain.model.AppItem
import com.example.homework8.domain.repository.AppsRepository
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val dataSource: FakeAppsDataSource,
    private val mapper: AppsMapper
) : AppsRepository {

    override suspend fun getApps(): List<AppItem> {
        val rawData = dataSource.fetchApps()
        return mapper.mapListToDomain(rawData)
    }
}