package ru.androidlearning.moviesearch.viewmodel

import ru.androidlearning.moviesearch.model.MovieDetailsDTO

sealed class MovieDetailsLoadState{
    data class Success(val movieDetailsDTO: MovieDetailsDTO): MovieDetailsLoadState()
    data class Error(val error: Throwable): MovieDetailsLoadState()
    object Loading: MovieDetailsLoadState()
}
