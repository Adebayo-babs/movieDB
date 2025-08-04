package com.example.moviedb.data.repository

import com.example.moviedb.data.api.MovieApiService
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.model.MovieCategory
import com.example.moviedb.data.model.MovieResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: MovieApiService
) {
    private val apiKey = "fa3c4fd2e5c77107bc28a4b6d52fcaf7"


    suspend fun getPopularMovies(page: Int = 1): Result<MovieResponse> {
        return try {
            val response = apiService.getPopularMovies(page = page, apiKey = apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNowPlayingMovies(page: Int): Result<MovieResponse> {
        return try {
            val response = apiService.getNowPlayingMovies(page = page, apiKey = apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTopRatedMovies(page: Int): Result<MovieResponse> {
        return try {
            val response = apiService.getTopRatedMovies(page = page, apiKey = apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMoviesByCategory(category: MovieCategory, page: Int): Result<MovieResponse> {
        return when (category) {
            MovieCategory.POPULAR -> getPopularMovies(page)
            MovieCategory.NOW_PLAYING -> getNowPlayingMovies(page)
            MovieCategory.TOP_RATED -> getTopRatedMovies(page)
        }
    }

    suspend fun getMovieDetails(movieId: Int): Result<Movie> {
        return try {
            val response = apiService.getMovieDetails(movieId = movieId, apiKey = apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchMovies(query: String, page: Int = 1): Result<MovieResponse> {
        return try {
            val response = apiService.searchMovies(query = query, apiKey = apiKey, page = page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}