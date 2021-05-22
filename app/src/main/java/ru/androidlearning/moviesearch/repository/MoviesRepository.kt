package ru.androidlearning.moviesearch.repository

import retrofit2.Callback
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.model.MovieDetailsDTO
import ru.androidlearning.moviesearch.repository.db.MovieEntity

interface MoviesRepository {
    fun getMoviesListFromServer(
        moviesListWebLoaderListener: MoviesListWebLoaderListener,
        language: String,
        useAdultsContent: Boolean,
        pageNumber: Int = 1
    )

    fun getMovieDetailsFromServer(
        callback: Callback<MovieDetailsDTO>,
        movieId: Int,
        language: String
    )

    fun searchMovies(
        moviesListWebLoaderListener: MoviesListWebLoaderListener,
        query: String,
        language: String,
        useAdultsContent: Boolean
    )

    interface MoviesListWebLoaderListener {
        fun onSuccess(moviesList: List<Movie>)
        fun onFailed(throwable: Throwable)
    }

    interface MoviesListDBLoaderListener {
        fun onSuccess(moviesList: List<MovieEntity>)
        fun onFailed(throwable: Throwable)
    }

    fun getAllMoviesFromDB(moviesListDBLoaderListener: MoviesListDBLoaderListener)

    fun saveMovieToDB(movieEntity: MovieEntity)

    fun clearMoviesHistoryInDB()
}
