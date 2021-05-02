package ru.androidlearning.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidlearning.moviesearch.model.MoveRepository
import ru.androidlearning.moviesearch.model.MoveRepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val moveDetailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val moveRepository: MoveRepository = MoveRepositoryImpl()
) : ViewModel() {

    fun getMovieDetailsFromLocalSource() {
        moveDetailsLiveData.value = AppState.Loading

        Thread {
            sleep(1000)
            moveDetailsLiveData.postValue(AppState.Success(moveRepository.getMovieDetailsFromLocalStorage()))
            //moveDetailsLiveData.postValue(AppState.Error(Throwable("Error")))
        }.start()
    }

    fun getMovieDetailsLiveData() = moveDetailsLiveData

}