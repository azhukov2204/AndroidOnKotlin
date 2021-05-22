package ru.androidlearning.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidlearning.moviesearch.repository.MoviesRepository
import ru.androidlearning.moviesearch.repository.MoviesRepositoryImpl
import ru.androidlearning.moviesearch.repository.db.MovieEntity

class MoviesHistoryViewModel(
    val moviesHistoryLiveData: MutableLiveData<MoviesHistoryLoadState> = MutableLiveData()
) : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl()

    private val moviesListDBLoaderListener = object : MoviesRepository.MoviesListDBLoaderListener {
        override fun onSuccess(moviesList: List<MovieEntity>) {
            moviesHistoryLiveData.postValue(MoviesHistoryLoadState.Success(moviesList))
        }

        override fun onFailed(throwable: Throwable) {
            moviesHistoryLiveData.postValue(MoviesHistoryLoadState.Error(throwable))
        }
    }

    fun getMoviesHistoryFromDB() {
        moviesHistoryLiveData.value = MoviesHistoryLoadState.Loading
        moviesRepository.getAllMoviesFromDB(moviesListDBLoaderListener)
    }

    fun clearMoviesHistory() {
        moviesRepository.clearMoviesHistoryInDB()
    }
}
