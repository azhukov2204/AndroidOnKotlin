package ru.androidlearning.moviesearch.viewmodel

import ru.androidlearning.moviesearch.repository.db.MovieEntity

sealed class MoviesHistoryLoadState {
    data class Success(val movieEntities: List<MovieEntity>): MoviesHistoryLoadState()
    data class Error(val error: Throwable): MoviesHistoryLoadState()
    object Loading: MoviesHistoryLoadState()
}
