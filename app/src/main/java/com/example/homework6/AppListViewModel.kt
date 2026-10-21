package com.example.homework6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppListViewModel : ViewModel() {

    private val _screenState = MutableStateFlow<List<String>>(emptyList())
    val screenState: StateFlow<List<String>> = _screenState.asStateFlow()

    private val _uiEffect = Channel<UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        loadApplications()
    }

    private fun loadApplications() {
        _screenState.value = listOf("Telegram", "WhatsApp", "YouTube", "Studio", "Browser")
    }

    fun onToolbarClicked() {
        viewModelScope.launch {
            _uiEffect.send(UiEffect.ShowSnackbar("Информационный Snackbar!"))
        }
    }

    sealed interface UiEffect {
        data class ShowSnackbar(val message: String) : UiEffect
    }
}