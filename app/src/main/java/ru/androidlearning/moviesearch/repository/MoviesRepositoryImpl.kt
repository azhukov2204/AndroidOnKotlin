package ru.androidlearning.moviesearch.repository

import retrofit2.Callback
import ru.androidlearning.moviesearch.model.MovieDetailsDTO

class MoviesRepositoryImpl: MoviesRepository {
    private val moviesRemoteDataSource by lazy { MoviesRemoteDataSource() }

    override fun getMoviesListFromServer(
        moviesListLoaderListener: MoviesRepository.MoviesListLoaderListener,
        language: String,
        pageNumber: Int
    ) {
        moviesRemoteDataSource.getMoviesList(moviesListLoaderListener, language, pageNumber)
    }

    override fun getMovieDetailsFromServer(
        callback: Callback<MovieDetailsDTO>,
        movieId: Int,
        language: String
    ) {
        moviesRemoteDataSource.getMovieDetails(callback, movieId, language)
    }

    override fun searchMovies(moviesListLoaderListener: MoviesRepository.MoviesListLoaderListener, query: String, language: String) {
        moviesRemoteDataSource.searchMovies(moviesListLoaderListener, query, language)
    }
}
