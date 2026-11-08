package com.example.homework9.presentation.catalog

import com.example.homework9.domain.model.AppItem

sealed interface CatalogUiState {
    data object Loading : CatalogUiState
    data class Success(val apps: List<AppItem>) : CatalogUiState
    data class Error(val message: String) : CatalogUiState
}