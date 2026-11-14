package com.example.homework10.ui.catalog

import com.example.homework10.domain.model.AppInfo

data class CatalogState(
    val isLoading: Boolean = true,
    val apps: List<AppInfo> = emptyList(),
    val sequentialTimeMs: Long? = null,
    val parallelTimeMs: Long? = null,
    val currentStatusText: String = ""
)