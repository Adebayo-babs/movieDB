package com.example.moviedb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviedb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var lastQuery: String? = null

    fun searchMovies(query: String) {
        if (query.isBlank() || query == lastQuery) return
        lastQuery = query
        currentPage = 1
        _uiState.value = HomeUiState(isLoading = true)

        viewModelScope.launch {
            repository.searchMovies(query, currentPage).fold(
                onSuccess = { response ->
                    _uiState.value = HomeUiState(
                        movies = response.results,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }
}