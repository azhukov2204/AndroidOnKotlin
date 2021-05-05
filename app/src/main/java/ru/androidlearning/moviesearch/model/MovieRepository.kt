package ru.androidlearning.moviesearch.model

interface MovieRepository {
    fun getMovieDetailsFromServer(): MovieDetails
    fun getMovieDetailsFromLocalStorage(): MovieDetails
}
