package ru.androidlearning.moviesearch.viewmodel

import ru.androidlearning.moviesearch.model.Movie

sealed class MoviesListLoadState {
    data class Success(val movies: List<Movie>): MoviesListLoadState()
    data class Error(val error: Throwable): MoviesListLoadState()
    object Loading: MoviesListLoadState()
}
