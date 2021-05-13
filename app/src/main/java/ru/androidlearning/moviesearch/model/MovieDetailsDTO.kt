package ru.androidlearning.moviesearch.model

data class MovieDetailsDTO(
    val runtime: Int? //пока буду забирать только одно поле, которое предусмотрено в текущем UI. Затем добавлю другие поля по мере необходимости
)
