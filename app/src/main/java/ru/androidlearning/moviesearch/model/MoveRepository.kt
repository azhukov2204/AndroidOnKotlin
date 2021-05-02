package ru.androidlearning.moviesearch.model

interface MoveRepository {
    fun getMoveDetailsFromServer(): MoveDetails
    fun getMoveDetailsFromLocalStorage(): MoveDetails
}