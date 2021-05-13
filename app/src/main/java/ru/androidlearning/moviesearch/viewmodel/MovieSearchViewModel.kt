package ru.androidlearning.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidlearning.moviesearch.model.*
import java.lang.Thread.sleep
import java.util.*

const val sleepTimeInMills: Long = 10000

class MovieSearchViewModel(
    private val moviesLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val movieRepository: MovieRepository = MovieRepositoryImpl()
) : ViewModel() {

    private val random = Random()

    fun getMoviesFromLocalSource() {
        moviesLiveData.value = AppState.Loading
        Thread {
            sleep(sleepTimeInMills)
            moviesLiveData.postValue(
                if (random.nextInt(2) == 0) AppState.Success(movieRepository.getMoviesFromLocalStorage())
                else AppState.Error(Throwable("Error of movies list load"))
            )
        }.start()
    }

    fun getMoviesFromServer() {
        moviesLiveData.value = AppState.Loading
        movieRepository.getMoviesFromServer(object : MoviesListLoader.MoviesListLoaderListener {
            override fun onSuccess(moviesList: List<Movie>) {
                moviesLiveData.postValue(AppState.Success(moviesList))
            }

            override fun onFailed(throwable: Throwable) {
                moviesLiveData.postValue(AppState.Error(throwable))
            }
        })
    }

    fun getMovieDetailsLiveData() = moviesLiveData
}
