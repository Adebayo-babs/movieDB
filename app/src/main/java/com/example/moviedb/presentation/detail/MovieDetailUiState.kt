package com.example.moviedb.presentation.detail

import com.example.moviedb.data.model.Movie

data class MovieDetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)