package ru.androidlearning.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidlearning.moviesearch.model.*
import ru.androidlearning.moviesearch.repository.*

class MovieSearchViewModel(
    private val moviesListsLiveData: MutableLiveData<MoviesListLoadState> = MutableLiveData(),
    private val moviesSearchLiveData: MutableLiveData<MoviesListLoadState> = MutableLiveData()
) : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl()
    private val moviesListWebLoaderListener: MoviesRepository.MoviesListWebLoaderListener = object :
        MoviesRepository.MoviesListWebLoaderListener {
        override fun onSuccess(moviesList: List<Movie>) {
            moviesListsLiveData.postValue(MoviesListLoadState.Success(moviesList))
        }

        override fun onFailed(throwable: Throwable) {
            moviesListsLiveData.postValue(MoviesListLoadState.Error(throwable))
        }
    }

    private val moviesSearchListenerWeb: MoviesRepository.MoviesListWebLoaderListener = object :
        MoviesRepository.MoviesListWebLoaderListener {
        override fun onSuccess(moviesList: List<Movie>) {
            moviesSearchLiveData.postValue(MoviesListLoadState.Success(moviesList))
        }

        override fun onFailed(throwable: Throwable) {
            moviesSearchLiveData.postValue(MoviesListLoadState.Error(throwable))
        }
    }

    fun getMoviesFromServer(language: String, useAdultsContent: Boolean) {
        moviesListsLiveData.value = MoviesListLoadState.Loading
        moviesRepository.getMoviesListFromServer(moviesListWebLoaderListener, language, useAdultsContent)
    }

    fun searchMovies(query: String, language: String, useAdultsContent: Boolean) {
        moviesSearchLiveData.value = MoviesListLoadState.Loading
        moviesRepository.searchMovies(moviesSearchListenerWeb, query, language, useAdultsContent)
    }

    fun getMovieDetailsLiveData() = moviesListsLiveData

    fun getMoviesSearchLiveData() = moviesSearchLiveData
}
