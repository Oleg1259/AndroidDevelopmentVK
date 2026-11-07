package com.example.homework8.data.mapper

import com.example.homework8.data.datasource.AppDto
import com.example.homework8.domain.model.AppItem
import javax.inject.Inject

class AppsMapper @Inject constructor() {
    fun mapToDomain(dto: AppDto): AppItem {
        return AppItem(
            id = dto.id,
            name = dto.rawTitle.replaceFirstChar { it.uppercase() }
        )
    }

    fun mapListToDomain(dtos: List<AppDto>): List<AppItem> {
        return dtos.map { mapToDomain(it) }
    }
}