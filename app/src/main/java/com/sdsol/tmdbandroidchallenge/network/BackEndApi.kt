package com.sdsol.tmdbandroidchallenge.network

import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface BackEndApi {

    @GET("discover/movie")
    fun getMovies(@Query("page") page : Int): Call<Objects>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId : Int): Call<Objects>

}