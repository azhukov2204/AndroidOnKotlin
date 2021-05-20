package ru.androidlearning.moviesearch.model

import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.androidlearning.moviesearch.BuildConfig
import ru.androidlearning.moviesearch.common_functions.READ_TIMEOUT
import ru.androidlearning.moviesearch.common_functions.getDateFromString
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private val GET_MOVIES_LISTS_REQUESTS = mapOf(
    "Popular" to URL("https://api.themoviedb.org/3/movie/popular?api_key=${BuildConfig.MOVIE_DB_API_KEY}&page=1&language=ru"),
    "Top Rated" to URL("https://api.themoviedb.org/3/movie/top_rated?api_key=${BuildConfig.MOVIE_DB_API_KEY}&page=1&language=ru"),
    "Upcoming" to URL("https://api.themoviedb.org/3/movie/upcoming?api_key=${BuildConfig.MOVIE_DB_API_KEY}&page=1&language=ru"),
    "Now Playing" to URL("https://api.themoviedb.org/3/movie/now_playing?api_key=${BuildConfig.MOVIE_DB_API_KEY}&page=1&language=ru"),
    "Latest" to URL("https://api.themoviedb.org/3/movie/latest?api_key=${BuildConfig.MOVIE_DB_API_KEY}&page=1&language=ru"),
)

class MoviesListLoader(private val moviesListLoaderListener: MoviesListLoaderListener) {
    private val moviesList = mutableListOf<Movie>()

    interface MoviesListLoaderListener {
        fun onSuccess(moviesList: List<Movie>)
        fun onFailed(throwable: Throwable)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMoviesList() {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            lateinit var urlConnection: HttpsURLConnection
            try {
                val getGenresListUrl =
                    URL("https://api.themoviedb.org/3/genre/movie/list?api_key=${BuildConfig.MOVIE_DB_API_KEY}&page=1&language=ru")
                urlConnection = getGenresListUrl.openConnection() as HttpsURLConnection
                val genresListDTO = getDTOFromUrl<GenresListDTO>(urlConnection)
                urlConnection.disconnect()
                for (request in GET_MOVIES_LISTS_REQUESTS) {
                    urlConnection = request.value.openConnection() as HttpsURLConnection
                    val moviesListDTO = getDTOFromUrl<MoviesListDTO>(urlConnection)
                    urlConnection.disconnect()
                    addMoviesListDTOtoMoviesList(moviesListDTO, genresListDTO, request.key)
                }
                handler.post { moviesListLoaderListener.onSuccess(moviesList) }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                handler.post { moviesListLoaderListener.onFailed(e) }
            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { moviesListLoaderListener.onFailed(e) }
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private inline fun <reified T> getDTOFromUrl(urlConnection: HttpsURLConnection): T? {
        urlConnection.requestMethod = "GET"
        urlConnection.readTimeout = READ_TIMEOUT
        val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
        return Gson().fromJson(getLines(bufferedReader), T::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun mapMovieItemDTOtoMovie(
        movieListItemDTO: MovieListItemDTO,
        genresListDTO: GenresListDTO?,
        movieCategory: String
    ) = Movie(
        id = movieListItemDTO.id,
        title = movieListItemDTO.title,
        releaseDate = movieListItemDTO.release_date?.let { getDateFromString(it) },
        rating = movieListItemDTO.vote_average,
        posterUri = Uri.parse(movieListItemDTO.poster_path),
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

    private fun addMoviesListDTOtoMoviesList(
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
}
