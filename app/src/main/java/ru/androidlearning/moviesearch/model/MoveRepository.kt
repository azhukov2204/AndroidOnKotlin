package ru.androidlearning.moviesearch.model

interface MoveRepository {
    fun getMovieDetailsFromServer(): MovieDetails
    fun getMovieDetailsFromLocalStorage(): MovieDetails
}