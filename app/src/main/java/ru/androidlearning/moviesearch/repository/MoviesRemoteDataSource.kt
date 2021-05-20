package ru.androidlearning.moviesearch.repository

import android.os.Handler
import android.os.Looper
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidlearning.moviesearch.BuildConfig
import ru.androidlearning.moviesearch.model.*
import ru.androidlearning.moviesearch.repository.api.*
import java.lang.Exception

const val BASE_URL = "https://api.themoviedb.org/"
const val BASE_MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/"
const val ERROR_LOADING_GENRES = "Error with loading genres list"
const val ERROR_LOADING_MOVIES_LIST = "Error with loading movies list"

private val moviesAPIClassesMap = mapOf(
    "Latest" to MoviesListLatestAPI::class.java,
    "Now Playing" to MoviesListNowPlayingAPI::class.java,
    "Upcoming" to MoviesListUpcomingAPI::class.java,
    "Popular" to MoviesListPopularAPI::class.java,
    "Top Rated" to MoviesListTopRatedAPI::class.java
)

private val genresAPIClass = GenresListAPI::class.java
private val movieDetailsAPIClass = MovieDetailsAPI::class.java
private val movieSearchAPIClass = MoviesSearchAPI::class.java

class MoviesRemoteDataSource {

    fun searchMovies(
        moviesListLoaderListener: MoviesRepository.MoviesListLoaderListener,
        query: String,
        language: String
    ) {
        val moviesList = mutableListOf<Movie>()
        val handler = Handler(Looper.getMainLooper())
        Thread {
            try {
                val genresResponse =
                    buildAPI(genresAPIClass).getGenres(BuildConfig.MOVIE_DB_API_KEY, language)
                        .execute()
                val genresListDTO = genresResponse.body()
                if (genresResponse.isSuccessful && genresListDTO != null) {
                    val moviesListResponse = buildAPI(movieSearchAPIClass).getMoviesList(
                        BuildConfig.MOVIE_DB_API_KEY,
                        query,
                        language
                    ).execute()
                    val moviesListDTO = moviesListResponse.body()
                    if (moviesListResponse.isSuccessful && moviesListDTO != null) {
                        addMoviesListDTOtoMoviesList(moviesList, moviesListDTO, genresListDTO, "")
                    } else {
                        handler.post {
                            moviesListLoaderListener.onFailed(
                                Throwable(
                                    ERROR_LOADING_MOVIES_LIST
                                )
                            )
                        }
                    }
                    handler.post { moviesListLoaderListener.onSuccess(moviesList) }
                } else {
                    handler.post { moviesListLoaderListener.onFailed(Throwable(ERROR_LOADING_GENRES)) }
                }
            } catch (e: Exception) {
                handler.post { moviesListLoaderListener.onFailed(e) }
            }
        }.start()
    }

    fun getMovieDetails(callback: Callback<MovieDetailsDTO>, movieId: Int, language: String) {
        val urlWithMovieId = "$BASE_MOVIE_DETAILS_URL$movieId"
        buildAPI(movieDetailsAPIClass).getMovieDetails(
            urlWithMovieId,
            BuildConfig.MOVIE_DB_API_KEY,
            language,
        ).enqueue(callback)
    }

    fun getMoviesList(
        moviesListLoaderListener: MoviesRepository.MoviesListLoaderListener,
        language: String,
        pageNumber: Int = 1
    ) {
        val moviesList = mutableListOf<Movie>()
        val handler = Handler(Looper.getMainLooper())
        Thread {
            try {
                val genresResponse =
                    buildAPI(genresAPIClass).getGenres(BuildConfig.MOVIE_DB_API_KEY, language)
                        .execute()
                val genresListDTO = genresResponse.body()
                if (genresResponse.isSuccessful && genresListDTO != null) {
                    for (moviesAPIEntry in moviesAPIClassesMap) {
                        val moviesListResponse = buildAPI(moviesAPIEntry.value).getMoviesList(
                            BuildConfig.MOVIE_DB_API_KEY,
                            pageNumber,
                            language
                        ).execute()
                        val moviesListDTO = moviesListResponse.body()
                        if (moviesListResponse.isSuccessful && moviesListDTO != null) {
                            addMoviesListDTOtoMoviesList(
                                moviesList,
                                moviesListDTO,
                                genresListDTO,
                                moviesAPIEntry.key
                            )
                        } else {
                            handler.post {
                                moviesListLoaderListener.onFailed(
                                    Throwable(
                                        ERROR_LOADING_MOVIES_LIST
                                    )
                                )
                            }
                        }
                    }
                    handler.post { moviesListLoaderListener.onSuccess(moviesList) }
                } else {
                    handler.post { moviesListLoaderListener.onFailed(Throwable(ERROR_LOADING_GENRES)) }
                }
            } catch (e: Exception) {
                handler.post { moviesListLoaderListener.onFailed(e) }
            }
        }.start()
    }

    private fun <T> buildAPI(service: Class<T>): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(service)

    private fun addMoviesListDTOtoMoviesList(
        moviesList: MutableList<Movie>,
        moviesListDTO: MoviesListDTO?,
        genresListDTO: GenresListDTO?,
        movieCategory: String
    ) {
        moviesListDTO?.results?.run {
            for (movieDTO in this) {
                moviesList.add(mapMovieItemDTOtoMovie(movieDTO, genresListDTO, movieCategory))
            }
        }
    }

    private fun mapMovieItemDTOtoMovie(
        movieListItemDTO: MovieListItemDTO,
        genresListDTO: GenresListDTO?,
        movieCategory: String
    ) = Movie(
        id = movieListItemDTO.id,
        title = movieListItemDTO.title,
        releaseDate = movieListItemDTO.release_date,
        rating = movieListItemDTO.vote_average,
        posterUri = movieListItemDTO.poster_path,
        genre = getGenresStringFromIds(movieListItemDTO.genre_ids, genresListDTO),
        durationInMinutes = 0,
        description = movieListItemDTO.overview,
        category = movieCategory
    )

    private fun getGenresStringFromIds(
        genreIds: List<Int>?,
        genresListDTO: GenresListDTO?
    ): String {
        var genresString = ""
        if (genreIds != null) {
            for (genreId in genreIds) {
                genresString += "${genresListDTO?.genres?.find { it.id == genreId }?.name}, "
            }
        }
        return genresString.removeSuffix(", ")
    }
}
