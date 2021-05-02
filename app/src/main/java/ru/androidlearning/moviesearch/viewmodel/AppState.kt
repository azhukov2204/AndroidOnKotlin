package ru.androidlearning.moviesearch.viewmodel

import ru.androidlearning.moviesearch.model.MoveDetails

sealed class AppState {
    data class Success(val moveDetails: MoveDetails): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()

}
