package com.example.moviedb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviedb.presentation.detail.MovieDetailScreen
import com.example.moviedb.presentation.home.HomeScreen
import com.example.moviedb.presentation.home.SearchScreen

@Composable
fun MovieApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                onMovieClick = { movieId ->
                    navController.navigate("movieDetail/$movieId")
                }
            )
        }

        composable(
            "movieDetail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: throw IllegalArgumentException("MovieId missing in navigation")
            MovieDetailScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )

        }

        composable("search") {
            SearchScreen(
                navController = navController,
                onMovieClick = { movieId ->
                    navController.navigate("movieDetail/$movieId")
            })
        }
    }
}

