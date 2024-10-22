package com.example.myflixster.network

import com.example.myflixster.models.ActorResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ActorService {
    @GET("person/popular")
    suspend fun getPopularActors(@Query("api_key") apiKey: String): ActorResponse
}

class ActorRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val actorService = retrofit.create(ActorService::class.java)

    suspend fun getPopularActors(apiKey: String): ActorResponse? {
        return actorService.getPopularActors(apiKey)
    }
}
