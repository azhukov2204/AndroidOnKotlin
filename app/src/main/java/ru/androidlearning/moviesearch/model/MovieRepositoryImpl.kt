package ru.androidlearning.moviesearch.model

class MovieRepositoryImpl : MovieRepository {
    override fun getMoviesFromServer() = getMovies()
    override fun getMoviesFromLocalStorage() = getMovies()
}
