package ru.androidlearning.moviesearch.model.web

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsDTO(
    @SerializedName("runtime")
    val runtime: Int? //пока буду забирать только одно поле, которое предусмотрено в текущем UI. Затем добавлю другие поля по мере необходимости
): Parcelable
