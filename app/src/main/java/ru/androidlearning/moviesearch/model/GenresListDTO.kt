package ru.androidlearning.moviesearch.model

import com.google.gson.annotations.SerializedName

data class GenresListDTO(
    val genres: List<GenresListItemDTO>
)

data class GenresListItemDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
