package com.example.homework8.presentation.apps


import com.example.homework8.domain.model.AppItem

sealed interface AppsUiState {
    data object Loading : AppsUiState
    data class Success(val apps: List<AppItem>) : AppsUiState
    data class Error(val message: String) : AppsUiState
}
