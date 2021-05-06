package ru.androidlearning.moviesearch.viewmodel

import ru.androidlearning.moviesearch.model.Movie

sealed class AppState {
    data class Success(val movies: List<Movie>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}
