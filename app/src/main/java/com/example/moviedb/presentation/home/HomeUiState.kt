package com.example.moviedb.presentation.home

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.model.MovieCategory

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: MovieCategory = MovieCategory.POPULAR,
    val showCategoryDropdown: Boolean = false
)
