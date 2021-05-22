package ru.androidlearning.moviesearch.repository.web.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.androidlearning.moviesearch.model.MovieDetailsDTO

interface MovieDetailsAPI {
    @GET
    fun getMovieDetails(
        @Url url: String,
        @Query("api_key") token: String,
        @Query("language") language: String,
    ): Call<MovieDetailsDTO>
}
