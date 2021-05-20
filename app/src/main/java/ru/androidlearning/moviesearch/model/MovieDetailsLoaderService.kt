package ru.androidlearning.moviesearch.model

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.androidlearning.moviesearch.BuildConfig
import ru.androidlearning.moviesearch.common_functions.READ_TIMEOUT
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val MOVIE_DETAILS_LOADER_INTENT_FILTER = "MovieDetailsLoaderService"
const val MOVIE_DETAILS_RESULTS_BROADCAST_BUNDLE_KEY = "MovieDetailsLoaderServiceResults"
const val MOVIE_ID_BUNDLE_KEY = "MovieIdBundleKey"
const val NO_INTENT_MESSAGE = "No intent"
const val MOVIE_ID_IS_0_MESSAGE = "MovieId is 0"

class MovieDetailsLoaderService(name: String = "MovieDetailsLoaderService") : IntentService(name) {
    private val broadcastIntent = Intent(MOVIE_DETAILS_LOADER_INTENT_FILTER)

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            sendResults(MovieDetailsLoadingResult(false, null, Throwable(NO_INTENT_MESSAGE)))
        } else {
            val movieId = intent.getIntExtra(MOVIE_ID_BUNDLE_KEY, 0)
            if (movieId == 0) {
                sendResults(MovieDetailsLoadingResult(false, null, Throwable(MOVIE_ID_IS_0_MESSAGE)))
            } else {
                loadMovieDetails(movieId)
            }
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        lateinit var urlConnection: HttpsURLConnection
        try {
            val getMovieDetailsUrl =
                URL("https://api.themoviedb.org/3/movie/${movieId}?api_key=${BuildConfig.MOVIE_DB_API_KEY}&language=ru")
            urlConnection = getMovieDetailsUrl.openConnection() as HttpsURLConnection
            val movieDetailsDTO = getDTOFromUrl<MovieDetailsDTO>(urlConnection)
            urlConnection.disconnect()
            sendResults(MovieDetailsLoadingResult(true, movieDetailsDTO))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            sendResults(MovieDetailsLoadingResult(false, null, e))
        } catch (e: Exception) {
            e.printStackTrace()
            sendResults(MovieDetailsLoadingResult(false, null, e))
        } finally {
            urlConnection.disconnect()
        }
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

    private fun sendResults(movieDetailsLoadingResult: MovieDetailsLoadingResult) {
        broadcastIntent.putExtra(
            MOVIE_DETAILS_RESULTS_BROADCAST_BUNDLE_KEY,
            movieDetailsLoadingResult
        )
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}
