package com.example.homework8.presentation.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework8.domain.usecase.GetAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase
    // Примечание: если UseCase по заданию не нужен, сюда напрямую
    // передается private val repository: AppsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AppsUiState>(AppsUiState.Loading)
    val uiState: StateFlow<AppsUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _uiState.value = AppsUiState.Loading
            try {
                val result = getAppsUseCase()
                _uiState.value = AppsUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = AppsUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}
