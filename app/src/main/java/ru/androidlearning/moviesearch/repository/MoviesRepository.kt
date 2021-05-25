package ru.androidlearning.moviesearch.repository

import retrofit2.Callback
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.model.MovieDetailsDTO

interface MoviesRepository {
    fun getMoviesListFromServer(
        moviesListLoaderListener: MoviesListLoaderListener,
        language: String,
        pageNumber: Int = 1
    )

    fun getMovieDetailsFromServer(
        callback: Callback<MovieDetailsDTO>,
        movieId: Int,
        language: String
    )

    fun searchMovies(
        moviesListLoaderListener: MoviesListLoaderListener,
        query: String,
        language: String
    )

    interface MoviesListLoaderListener {
        fun onSuccess(moviesList: List<Movie>)
        fun onFailed(throwable: Throwable)
    }
}
