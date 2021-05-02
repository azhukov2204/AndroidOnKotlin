package ru.androidlearning.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidlearning.moviesearch.model.MoveRepository
import ru.androidlearning.moviesearch.model.MoveRepositoryImpl
import java.lang.Thread.sleep
import java.util.*

class MovieDetailViewModel(
    private val moveDetailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val moveRepository: MoveRepository = MoveRepositoryImpl()
) : ViewModel() {

    private val random = Random()

    fun getMovieDetailsFromLocalSource() {
        moveDetailsLiveData.value = AppState.Loading

        Thread {
            sleep(1000)
            val appState =
                if (random.nextInt(2) == 0) AppState.Success(moveRepository.getMovieDetailsFromLocalStorage())
                else AppState.Error(Throwable("Error of detail load"))
            moveDetailsLiveData.postValue(appState)
        }.start()
    }

    fun getMovieDetailsLiveData() = moveDetailsLiveData
}