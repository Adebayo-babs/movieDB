package com.example.moviedb.presentation.home

import com.example.moviedb.data.model.Movie

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
