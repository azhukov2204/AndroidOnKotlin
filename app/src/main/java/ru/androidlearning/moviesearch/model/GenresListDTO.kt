package ru.androidlearning.moviesearch.model

data class GenresListDTO(
    val genres: List<GenresListItemDTO>
)

data class GenresListItemDTO(
    val id: Int,
    val name: String
)
