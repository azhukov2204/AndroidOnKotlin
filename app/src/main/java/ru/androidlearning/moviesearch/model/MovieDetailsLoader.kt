package ru.androidlearning.moviesearch.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.androidlearning.moviesearch.BuildConfig
import ru.androidlearning.moviesearch.common_functions.READ_TIMEOUT
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieDetailsLoader (private val movieDetailsLoaderListener: MovieDetailsLoaderListener) {

    interface MovieDetailsLoaderListener {
        fun onSuccess(movieDetailsDTO: MovieDetailsDTO?)
        fun onFailed(throwable: Throwable)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadDetailsList(movieId: Int) {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            lateinit var urlConnection: HttpsURLConnection
            try {
                val getMovieDetailsUrl =
                    URL("https://api.themoviedb.org/3/movie/${movieId}?api_key=${BuildConfig.MOVIE_DB_API_KEY}&language=ru")
                urlConnection = getMovieDetailsUrl.openConnection() as HttpsURLConnection
                val movieDetailsDTO = getDTOFromUrl<MovieDetailsDTO>(urlConnection)
                urlConnection.disconnect()
                handler.post { movieDetailsLoaderListener.onSuccess(movieDetailsDTO) }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                handler.post { movieDetailsLoaderListener.onFailed(e) }
            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { movieDetailsLoaderListener.onFailed(e) }
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
}
