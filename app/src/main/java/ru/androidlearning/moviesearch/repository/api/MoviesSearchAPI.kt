package ru.androidlearning.moviesearch.repository.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidlearning.moviesearch.model.MoviesListDTO

interface MoviesSearchAPI {
    @GET("3/search/movie")
    fun getMoviesList(
        @Query("api_key") token: String,
        @Query("query") query: String,
        @Query("language") language: String
    ): Call<MoviesListDTO>
}
