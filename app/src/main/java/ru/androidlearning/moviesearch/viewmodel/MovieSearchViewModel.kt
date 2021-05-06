package ru.androidlearning.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidlearning.moviesearch.model.MovieRepository
import ru.androidlearning.moviesearch.model.MovieRepositoryImpl
import java.lang.Thread.sleep
import java.util.*

const val sleepTimeInMills: Long = 1000

class MovieSearchViewModel(
    private val moviesLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val movieRepository: MovieRepository = MovieRepositoryImpl()
) : ViewModel() {

    private val random = Random()

    fun getMoviesFromLocalSource() {
        moviesLiveData.value = AppState.Loading
        Thread {
            sleep(sleepTimeInMills)
            /*val appState =
                if (random.nextInt(2) == 0) AppState.Success(movieRepository.getMoviesFromLocalStorage())
                else AppState.Error(Throwable("Error of detail load"))*/
            val appState = AppState.Success(movieRepository.getMoviesFromLocalStorage())
            moviesLiveData.postValue(appState)
        }.start()
    }

    fun getMovieDetailsLiveData() = moviesLiveData
}
