package com.example.homework9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework9.data.remote.RetrofitClient
import com.example.homework9.data.repository.CatalogRepositoryImpl
import com.example.homework9.domain.usecase.GetAppDetailsUseCase
import com.example.homework9.domain.usecase.GetCatalogUseCase
import com.example.homework9.presentation.catalog.CatalogScreen
import com.example.homework9.presentation.catalog.CatalogViewModel
import com.example.homework9.presentation.theme.AppCatalogTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация зависимостей
        val repository = CatalogRepositoryImpl(RetrofitClient.api)
        val getCatalogUseCase = GetCatalogUseCase(repository)

        // Подготовленный UseCase для детальной информации (понадобится в следующих заданиях)
        val getAppDetailsUseCase = GetAppDetailsUseCase(repository)

        val viewModelFactory = CatalogViewModel.Factory(getCatalogUseCase)

        setContent {
            AppCatalogTheme {
                val catalogViewModel: CatalogViewModel = viewModel(factory = viewModelFactory)
                CatalogScreen(viewModel = catalogViewModel)
            }
        }
    }
}