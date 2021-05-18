package ru.androidlearning.moviesearch.model

import android.os.Build
import androidx.annotation.RequiresApi

class MovieRepositoryImpl : MovieRepository {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getMoviesFromServer(moviesListLoaderListener: MoviesListLoader.MoviesListLoaderListener) {
        MoviesListLoader(moviesListLoaderListener).loadMoviesList()
    }

    override fun getMoviesFromLocalStorage() = getMovies()
}
