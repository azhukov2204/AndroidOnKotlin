package ru.androidlearning.moviesearch.repository.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidlearning.moviesearch.model.GenresListDTO

interface GenresListAPI {
    @GET("3/genre/movie/list")
    fun getGenres(
        @Query("api_key") token: String,
        @Query("language") language: String
    ): Call<GenresListDTO>
}
