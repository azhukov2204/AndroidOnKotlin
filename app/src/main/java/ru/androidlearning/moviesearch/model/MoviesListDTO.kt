package ru.androidlearning.moviesearch.model

data class MoviesListDTO(
    val results: List<MovieListItemDTO>?
)

data class MovieListItemDTO(
    val id: Int?,
    val title: String?,
    val release_date: String?,
    val vote_average: Double?,
    val poster_path: String?,
    val genre_ids: List<Int>?,
    val overview: String?
)
