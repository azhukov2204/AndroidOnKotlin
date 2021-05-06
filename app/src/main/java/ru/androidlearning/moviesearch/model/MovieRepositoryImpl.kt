package ru.androidlearning.moviesearch.model

class MovieRepositoryImpl : MovieRepository {
    override fun getMoviesFromServer(): List<Movie> {
        return getMovies()
    }

    override fun getMoviesFromLocalStorage(): List<Movie> {
        return getMovies()
    }
}
