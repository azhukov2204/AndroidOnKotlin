package ru.androidlearning.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import ru.androidlearning.moviesearch.model.MovieDetailsDTO
import ru.androidlearning.moviesearch.repository.MoviesRepository
import ru.androidlearning.moviesearch.repository.MoviesRepositoryImpl
import retrofit2.Callback
import retrofit2.Response
import ru.androidlearning.moviesearch.repository.db.MovieEntity

const val ERROR_CORRUPT_DATA = "Corrupt data"

class MovieDetailsViewModel(
    private val movieDetailsLiveData: MutableLiveData<MovieDetailsLoadState> = MutableLiveData(),
) : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl()
    private val callback: Callback<MovieDetailsDTO> = object : Callback<MovieDetailsDTO> {
        override fun onResponse(
            call: Call<MovieDetailsDTO>,
            response: Response<MovieDetailsDTO>
        ) {
            val movieDetailsDTO = response.body()
            if (response.isSuccessful && movieDetailsDTO != null) {
                movieDetailsLiveData.postValue(MovieDetailsLoadState.Success(movieDetailsDTO))
            } else {
                movieDetailsLiveData.postValue(
                    MovieDetailsLoadState.Error(Throwable(ERROR_CORRUPT_DATA))
                )
            }
        }

        override fun onFailure(call: Call<MovieDetailsDTO>, t: Throwable) {
            movieDetailsLiveData.postValue(MovieDetailsLoadState.Error(Throwable(t.message)))
        }
    }

    fun getMovieDetailsFromServer(movieId: Int, language: String) {
        movieDetailsLiveData.value = MovieDetailsLoadState.Loading
        moviesRepository.getMovieDetailsFromServer(callback, movieId, language)
    }

    fun getMovieDetailsLiveData() = movieDetailsLiveData

    fun saveMovieToDB(movieEntity: MovieEntity) {
        moviesRepository.saveMovieToDB(movieEntity)
    }
}
