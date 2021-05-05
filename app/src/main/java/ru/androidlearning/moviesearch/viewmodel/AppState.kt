package ru.androidlearning.moviesearch.viewmodel

import ru.androidlearning.moviesearch.model.MovieDetails

sealed class AppState {
    data class Success(val movieDetails: MovieDetails): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}
