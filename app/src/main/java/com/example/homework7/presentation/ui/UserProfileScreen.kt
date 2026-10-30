package com.example.homework7.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework7.domain.model.User
import com.example.homework7.presentation.state.UserUiState
import com.example.homework7.presentation.viewmodel.UserViewModel

// Jetpack Compose верстка экрана
@Composable
fun UserProfileScreen(
    viewModel: UserViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = state) {
            is UserUiState.Loading -> {
                CircularProgressIndicator()
            }
            is UserUiState.Success -> {
                UserContent(user = currentState.user)
            }
            is UserUiState.Error -> {
                Text(
                    text = "Ошибка: ${currentState.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun UserContent(user: User) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Профиль пользователя", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "ID: ${user.id}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "ФИО: ${user.fullName}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyLarge)
    }
}