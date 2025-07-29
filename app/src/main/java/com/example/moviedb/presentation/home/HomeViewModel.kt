package com.example.moviedb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false
    private var canLoadMore = true

    init {
        loadMovies()
    }

    fun loadMovies() {
        if (_uiState.value.isLoading || !canLoadMore) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.getPopularMovies(currentPage).fold(
                onSuccess = { response ->
                    if (response.results.isEmpty()) {
                        isLastPage = true
                    } else {
                        _uiState.value = _uiState.value.copy(
                            movies = _uiState.value.movies + response.results,
                            isLoading = false,
                            error = null
                        )
                        currentPage++
                    }
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun retry() {
        currentPage = 1
        canLoadMore = true
        loadMovies()
    }
}