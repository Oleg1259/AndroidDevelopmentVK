package com.example.homework9.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.homework9.domain.usecase.GetCatalogUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val getCatalogUseCase: GetCatalogUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        loadCatalog()
    }

    fun loadCatalog() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            getCatalogUseCase()
                .onSuccess { apps ->
                    _uiState.value = CatalogUiState.Success(apps)
                }
                .onFailure { exception ->
                    _uiState.value = CatalogUiState.Error(
                        exception.localizedMessage ?: "Не удалось загрузить каталог"
                    )
                }
        }
    }

    class Factory(
        private val getCatalogUseCase: GetCatalogUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatalogViewModel(getCatalogUseCase) as T
        }
    }
}