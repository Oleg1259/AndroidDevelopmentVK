package com.example.homework10.ui.details

import com.example.homework10.domain.model.AppInfo

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState // 0..100
    data object Installed : InstallState
}

sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data object Error : AppDetailsState

    data class Content(
        val app: AppInfo,
        val installState: InstallState = InstallState.NotInstalled
    ) : AppDetailsState
}