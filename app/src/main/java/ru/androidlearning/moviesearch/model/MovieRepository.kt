package ru.androidlearning.moviesearch.model

interface MovieRepository {
    fun getMoviesFromServer(moviesListLoaderListener: MoviesListLoader.MoviesListLoaderListener)
    fun getMoviesFromLocalStorage(): List<Movie>
}
