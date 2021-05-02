package ru.androidlearning.moviesearch.model

class MoveRepositoryImpl: MoveRepository {
    override fun getMoveDetailsFromServer(): MoveDetails {
        return MoveDetails()
    }

    override fun getMoveDetailsFromLocalStorage(): MoveDetails {
        return MoveDetails()
    }
}