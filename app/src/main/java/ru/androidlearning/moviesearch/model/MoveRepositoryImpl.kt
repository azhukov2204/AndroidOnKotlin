package ru.androidlearning.moviesearch.model

class MoveRepositoryImpl: MoveRepository {
    override fun getMovieDetailsFromServer(): MovieDetails {
        return MovieDetails()
    }

    override fun getMovieDetailsFromLocalStorage(): MovieDetails {
        return MovieDetails()
    }
}