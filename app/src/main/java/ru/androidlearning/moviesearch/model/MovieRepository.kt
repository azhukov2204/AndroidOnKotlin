package ru.androidlearning.moviesearch.model

interface MovieRepository {
    fun getMoviesFromServer(): List<Movie>
    fun getMoviesFromLocalStorage(): List<Movie>
}
