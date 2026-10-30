package com.example.homework7.presentation.state

import com.example.homework7.domain.model.User

// Состояния экрана для обработки загрузки, успеха и ошибок
sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val user: User) : UserUiState
    data class Error(val message: String) : UserUiState
}
