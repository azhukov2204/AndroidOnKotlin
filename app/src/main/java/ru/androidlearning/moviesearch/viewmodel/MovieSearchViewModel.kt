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
    private val moviesListLoaderListener: MoviesRepository.MoviesListLoaderListener = object :
        MoviesRepository.MoviesListLoaderListener {
        override fun onSuccess(moviesList: List<Movie>) {
            moviesListsLiveData.postValue(MoviesListLoadState.Success(moviesList))
        }

        override fun onFailed(throwable: Throwable) {
            moviesListsLiveData.postValue(MoviesListLoadState.Error(throwable))
        }
    }

    private val moviesSearchListener: MoviesRepository.MoviesListLoaderListener = object :
        MoviesRepository.MoviesListLoaderListener {
        override fun onSuccess(moviesList: List<Movie>) {
            moviesSearchLiveData.postValue(MoviesListLoadState.Success(moviesList))
        }

        override fun onFailed(throwable: Throwable) {
            moviesSearchLiveData.postValue(MoviesListLoadState.Error(throwable))
        }
    }

    fun getMoviesFromServer(language: String) {
        moviesListsLiveData.value = MoviesListLoadState.Loading
        moviesRepository.getMoviesListFromServer(moviesListLoaderListener, language)
    }

    fun searchMovies(query: String, language: String) {
        moviesSearchLiveData.value = MoviesListLoadState.Loading
        moviesRepository.searchMovies(moviesSearchListener, query, language)
    }

    fun getMovieDetailsLiveData() = moviesListsLiveData

    fun getMoviesSearchLiveData() = moviesSearchLiveData
}
