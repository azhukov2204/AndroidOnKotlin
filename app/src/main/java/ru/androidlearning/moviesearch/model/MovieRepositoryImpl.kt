package ru.androidlearning.moviesearch.model

class MovieRepositoryImpl: MovieRepository {
    override fun getMovieDetailsFromServer(): MovieDetails {
        return MovieDetails()
    }

    override fun getMovieDetailsFromLocalStorage(): MovieDetails {
        return MovieDetails()
    }
}
