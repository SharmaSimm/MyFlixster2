package com.example.myflixster.models

import com.example.myflixster.models.ActorResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.myflixster.models.MovieResponse

interface MovieApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String): ConfigurationResponse

    // Adding the new API call for fetching popular actors
    @GET("person/popular")
    suspend fun getPopularActors(@Query("api_key") apiKey: String): ActorResponse
}
