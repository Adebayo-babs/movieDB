package com.example.moviedb.data.model

class MovieResponse (
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)