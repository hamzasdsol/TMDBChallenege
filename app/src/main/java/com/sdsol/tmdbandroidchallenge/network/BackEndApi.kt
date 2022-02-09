package com.sdsol.tmdbandroidchallenge.network

import com.sdsol.tmdbandroidchallenge.models.moviedetails.MovieDetailResponse
import com.sdsol.tmdbandroidchallenge.models.movieslistresponse.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface BackEndApi {

    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page : Int) : Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId : Int): MovieDetailResponse

}