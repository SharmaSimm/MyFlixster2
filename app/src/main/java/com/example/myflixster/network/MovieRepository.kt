package com.example.myflixster.network

import com.example.myflixster.models.MovieApiService
import com.example.myflixster.models.MovieResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException

class MovieRepository {

    private val apiService: MovieApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(MovieApiService::class.java)
    }

    suspend fun getNowPlayingMovies(apiKey: String): MovieResponse? {
        return try {
            val response = apiService.getNowPlayingMovies(apiKey)
            response
        } catch (e: HttpException) {
            // Handle HTTP exceptions
            null
        } catch (e: Exception) {
            // Handle other exceptions
            null
        }
    }
}
