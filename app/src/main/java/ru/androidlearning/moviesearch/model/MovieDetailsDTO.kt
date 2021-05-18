package ru.androidlearning.moviesearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailsDTO(
    val runtime: Int? //пока буду забирать только одно поле, которое предусмотрено в текущем UI. Затем добавлю другие поля по мере необходимости
): Parcelable
