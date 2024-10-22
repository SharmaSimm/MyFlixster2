package com.example.myflixster.models

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myflixster.models.ActorAdapter // Import the adapter for actors
import com.example.myflixster.models.MovieAdapter
import com.example.myflixster.databinding.ActivityMainBinding
import com.example.myflixster.network.ActorRepository // Import repository for actors
import com.example.myflixster.network.ConfigurationRepository
import com.example.myflixster.network.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieRepository = MovieRepository()
    private val configurationRepository = ConfigurationRepository()
    private val actorRepository = ActorRepository() // New repository for actors
    private lateinit var baseImageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView setup for movies
        binding.rvMovies.layoutManager = LinearLayoutManager(this)

        // RecyclerView setup for actors (horizontal scrolling)
        binding.rvActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Fetch configuration and then movies and actors
        fetchConfigurationAndContent()
    }

    private fun fetchConfigurationAndContent() {
        lifecycleScope.launch {
            try {
                val apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

                // Fetch configuration
                val configurationResponse = withContext(Dispatchers.IO) {
                    configurationRepository.fetchConfiguration(apiKey)
                }

                if (configurationResponse != null) {
                    baseImageUrl = configurationResponse.images.base_url
                    // Fetch movies and actors after getting the configuration
                    fetchMovies(apiKey)
                    fetchActors(apiKey)
                } else {
                    Log.e("MainActivity", "ConfigurationResponse is null")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to fetch configuration", e)
            }
        }
    }

    private fun fetchMovies(apiKey: String) {
        lifecycleScope.launch {
            try {
                val movieResponse = withContext(Dispatchers.IO) {
                    movieRepository.getNowPlayingMovies(apiKey)
                }

                if (movieResponse != null) {
                    val movies = movieResponse.results ?: emptyList()
                    Log.d("MainActivity", "Movie list size: ${movies.size}")
                    val movieAdapter = MovieAdapter(movies, baseImageUrl)
                    binding.rvMovies.adapter = movieAdapter
                } else {
                    Log.e("MainActivity", "MovieResponse is null")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to fetch movies", e)
            }
        }
    }

    private fun fetchActors(apiKey: String) {
        lifecycleScope.launch {
            try {
                val actorResponse = withContext(Dispatchers.IO) {
                    actorRepository.getPopularActors(apiKey)
                }

                if (actorResponse != null) {
                    val actors = actorResponse.actors ?: emptyList()
                    Log.d("MainActivity", "Actor list size: ${actors.size}")
                    val actorAdapter = ActorAdapter(actors, baseImageUrl) // Pass actor data to adapter
                    binding.rvActors.adapter = actorAdapter
                } else {
                    Log.e("MainActivity", "ActorResponse is null")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to fetch actors", e)
            }
        }
    }
}
