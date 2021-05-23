package ru.androidlearning.moviesearch.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.androidlearning.moviesearch.model.Movie
import ru.androidlearning.moviesearch.model.db.MovieEntity
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd  HH:mm:ss"
fun getDateFromString(dateStr: String): Date? = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(dateStr)
fun getStringFromDate(date: Date): String? = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)

fun View.showSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    action: ((View) -> Unit)? = null
) {
    Snackbar.make(this, message, length).setAction(actionText, action).show()
}

fun mapMovieEntityToMovie(movieEntity: MovieEntity) = Movie(
    id = movieEntity.id,
    title = movieEntity.title,
    releaseDate = movieEntity.releaseDate,
    rating = movieEntity.rating,
    posterUri = movieEntity.posterUri,
    genre = movieEntity.genre,
    durationInMinutes = movieEntity.durationInMinutes,
    description = movieEntity.description,
    category = "",
    isAdult = movieEntity.isAdult
)