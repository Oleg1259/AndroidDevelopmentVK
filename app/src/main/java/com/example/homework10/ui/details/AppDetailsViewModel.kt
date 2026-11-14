package com.example.homework10.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework10.data.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppsRepository
) : ViewModel() {

    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private var installJob: Job? = null

    init {
        loadAppDetails()
    }

    private fun loadAppDetails() {
        viewModelScope.launch {
            val app = repository.getAppById(appId)
            _state.value = if (app != null) {
                AppDetailsState.Content(app = app)
            } else {
                AppDetailsState.Error
            }
        }
    }

    fun onInstallClick() {
        val current = _state.value
        if (current !is AppDetailsState.Content) return

        if (current.installState is InstallState.Installing) {
            installJob?.cancel()
            installJob = null
            updateInstallState(InstallState.NotInstalled)
        } else if (current.installState is InstallState.NotInstalled) {
            installJob = viewModelScope.launch {
                emulateInstall()
            }
        }
    }

    private suspend fun emulateInstall() {
        try {
            updateInstallState(InstallState.Installing(0))
            for (progress in 5..100 step 5) {
                delay(150)
                updateInstallState(InstallState.Installing(progress))
            }
            updateInstallState(InstallState.Installed)
        } catch (e: CancellationException) {
            updateInstallState(InstallState.NotInstalled)
            throw e
        } finally {
            installJob = null
        }
    }

    private fun updateInstallState(newInstallState: InstallState) {
        _state.update { currentState ->
            if (currentState is AppDetailsState.Content) {
                currentState.copy(installState = newInstallState)
            } else {
                currentState
            }
        }
    }
}