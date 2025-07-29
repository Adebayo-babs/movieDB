package com.example.moviedb.presentation.detail

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
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.getMovieDetails(movieId).fold(
                onSuccess = { movie ->
                    _uiState.value = _uiState.value.copy(
                        movie = movie,
                        isLoading = false,
                        error = null
                    )
                },
                onFailure = { error ->
                    val safeMessage = error.message ?: "Unexpected error"
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = safeMessage
                    )
                }
            )
        }
    }
}