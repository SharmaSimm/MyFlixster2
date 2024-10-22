package com.example.myflixster.network

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.example.myflixster.models.MovieApiService
import com.example.myflixster.models.ConfigurationResponse

class ConfigurationRepository {

    private val apiService: MovieApiService = RetrofitClient.instance

    suspend fun fetchConfiguration(apiKey: String): ConfigurationResponse? {
        return try {
            val response = apiService.getConfiguration(apiKey)
            Log.d("ConfigurationRepository", "Base URL: ${response.images.base_url}")
            response
        } catch (e: HttpException) {
            Log.e("ConfigurationRepository", "HTTP error", e)
            null
        } catch (e: Exception) {
            Log.e("ConfigurationRepository", "Error", e)
            null
        }
    }

}
