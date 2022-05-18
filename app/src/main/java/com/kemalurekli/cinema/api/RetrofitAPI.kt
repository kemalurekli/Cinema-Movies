package com.kemalurekli.cinema.api

import com.kemalurekli.cinema.model.MovieResult
import com.kemalurekli.cinema.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/")
    suspend fun searchMovie(
        @Query("t") searchQuery : String,
        @Query("apikey") apiKey :String = API_KEY
    ): Response<MovieResult>
}