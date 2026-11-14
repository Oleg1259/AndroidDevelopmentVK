package com.example.homework10.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("О приложении") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val currentState = state) {
                is AppDetailsState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is AppDetailsState.Error -> {
                    Text(
                        text = "Приложение не найдено",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is AppDetailsState.Content -> {
                    AppDetailsContent(
                        state = currentState,
                        onInstallClick = viewModel::onInstallClick
                    )
                }
            }
        }
    }
}

@Composable
private fun AppDetailsContent(
    state: AppDetailsState.Content,
    onInstallClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = state.app.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${state.app.developer} • ${state.app.category} • ${state.app.sizeMb} МБ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        InstallButton(
            installState = state.installState,
            onClick = onInstallClick,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.installState is InstallState.Installing) {
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { state.installState.progress / 100f },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Описание",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.app.description,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun InstallButton(
    installState: InstallState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonText = when (installState) {
        is InstallState.NotInstalled -> "Установить"
        is InstallState.Installing -> "Отменить (${installState.progress}%)"
        is InstallState.Installed -> "Установлено"
    }

    val buttonColors = if (installState is InstallState.Installing) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        )
    } else {
        ButtonDefaults.buttonColors()
    }

    Button(
        onClick = onClick,
        enabled = installState !is InstallState.Installed,
        colors = buttonColors,
        modifier = modifier
    ) {
        Text(text = buttonText)
    }
}
