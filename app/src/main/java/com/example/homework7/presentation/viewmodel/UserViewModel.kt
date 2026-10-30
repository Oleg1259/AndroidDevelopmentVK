package com.example.homework7.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework7.data.repository.UserRepositoryImpl
import com.example.homework7.domain.repository.UserRepository
import com.example.homework7.presentation.state.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Jetpack ViewModel управляет UI-состоянием и запрашивает данные из доменного слоя
class UserViewModel(
    // В будущем сюда будет внедряться зависимость через Hilt/Koin
    private val userRepository: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val user = userRepository.getUserProfile()
                _uiState.value = UserUiState.Success(user)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.localizedMessage ?: "Неизвестная ошибка")
            }
        }
    }
}
