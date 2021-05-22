package ru.androidlearning.moviesearch.repository

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import retrofit2.Callback
import ru.androidlearning.moviesearch.app.App
import ru.androidlearning.moviesearch.model.MovieDetailsDTO
import ru.androidlearning.moviesearch.repository.db.MovieDao
import ru.androidlearning.moviesearch.repository.db.MovieEntity
import ru.androidlearning.moviesearch.repository.web.MoviesRemoteDataSource
import java.lang.Exception

const val HANDLER_THREAD_NAME = "WorkThread"

class MoviesRepositoryImpl(
    private val movieDBDataSource: MovieDao = App.getMovieDao(),
    private val moviesRemoteDataSource: MoviesRemoteDataSource = MoviesRemoteDataSource()
) : MoviesRepository {

    companion object {
        private val handlerWorkThread = HandlerThread(HANDLER_THREAD_NAME)

        init {
            handlerWorkThread.start()
        }
    }


    override fun getMoviesListFromServer(
        moviesListWebLoaderListener: MoviesRepository.MoviesListWebLoaderListener,
        language: String,
        useAdultsContent: Boolean,
        pageNumber: Int
    ) {
        moviesRemoteDataSource.getMoviesList(
            moviesListWebLoaderListener,
            language,
            useAdultsContent,
            pageNumber
        )
    }

    override fun getMovieDetailsFromServer(
        callback: Callback<MovieDetailsDTO>,
        movieId: Int,
        language: String
    ) {
        moviesRemoteDataSource.getMovieDetails(callback, movieId, language)
    }

    override fun searchMovies(
        moviesListWebLoaderListener: MoviesRepository.MoviesListWebLoaderListener,
        query: String,
        language: String,
        useAdultsContent: Boolean
    ) {
        moviesRemoteDataSource.searchMovies(
            moviesListWebLoaderListener,
            query,
            language,
            useAdultsContent
        )
    }

    override fun getAllMoviesFromDB(moviesListDBLoaderListener: MoviesRepository.MoviesListDBLoaderListener) {
        val uiHandler = Handler(Looper.getMainLooper())
        Handler(handlerWorkThread.looper).post {
            try {
                val moviesEntitiesList = movieDBDataSource.getAllMoviesList()
                uiHandler.post { moviesListDBLoaderListener.onSuccess(moviesEntitiesList) }
            } catch (e: Exception) {
                uiHandler.post { moviesListDBLoaderListener.onFailed(e) }
            }
        }
    }

    override fun saveMovieToDB(movieEntity: MovieEntity) {
        Handler(handlerWorkThread.looper).post { movieDBDataSource.insert(movieEntity) }
    }

    override fun clearMoviesHistoryInDB() {
        Handler(handlerWorkThread.looper).post { movieDBDataSource.deleteAll() }

    }
}
