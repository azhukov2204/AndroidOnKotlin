package ru.androidlearning.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailsLoadingResult(
    val isSuccessful: Boolean,
    val movieDetailsDTO: MovieDetailsDTO?,
    val error: Throwable? = null
): Parcelable
