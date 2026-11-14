package com.example.homework10.ui.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework10.data.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogState())
    val state: StateFlow<CatalogState> = _state.asStateFlow()

    init {
        loadCatalogAndBenchmarkRatings()
    }

    private fun loadCatalogAndBenchmarkRatings() {
        viewModelScope.launch {
            val apps = repository.getApps()
            _state.update {
                it.copy(
                    isLoading = false,
                    apps = apps,
                    currentStatusText = "Загрузка рейтингов последовательно..."
                )
            }

            val ids = apps.map { it.id }

            // 1. Замер последовательной загрузки
            val seqTime = measureTimeMillis {
                val ratings = repository.loadRatingsSequentially(ids)
                updateAppsRatings(ratings)
            }
            Log.d("RatingsBenchmark", "Последовательно (${ids.size} шт.): $seqTime мс")

            // Сброс рейтингов перед параллельным замером
            _state.update { current ->
                current.copy(
                    sequentialTimeMs = seqTime,
                    apps = current.apps.map { it.copy(rating = null) },
                    currentStatusText = "Загрузка рейтингов параллельно..."
                )
            }

            // 2. Замер параллельной загрузки
            val parTime = measureTimeMillis {
                val ratings = repository.loadRatingsInParallel(ids)
                updateAppsRatings(ratings)
            }
            Log.d("RatingsBenchmark", "Параллельно (${ids.size} шт.): $parTime мс")

            _state.update {
                it.copy(
                    parallelTimeMs = parTime,
                    currentStatusText = "Замеры завершены"
                )
            }
        }
    }

    private fun updateAppsRatings(ratings: Map<String, Float>) {
        _state.update { current ->
            current.copy(
                apps = current.apps.map { app ->
                    app.copy(rating = ratings[app.id] ?: app.rating)
                }
            )
        }
    }
}