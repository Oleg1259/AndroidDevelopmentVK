package com.example.homework9.data.mapper

import com.example.homework9.data.remote.dto.AppDetailsDto
import com.example.homework9.data.remote.dto.AppItemDto
import com.example.homework9.domain.model.AppDetails
import com.example.homework9.domain.model.AppItem

fun AppItemDto.toDomain(): AppItem {
    return AppItem(
        id = id.orEmpty(),
        name = name ?: "Без названия",
        description = description.orEmpty(),
        category = category ?: "Без категории",
        iconUrl = iconUrl.orEmpty()
    )
}

fun AppDetailsDto.toDomain(): AppDetails {
    return AppDetails(
        id = id.orEmpty(),
        name = name ?: "Без названия",
        description = description.orEmpty(),
        category = category ?: "Без категории",
        iconUrl = iconUrl.orEmpty()
    )
}