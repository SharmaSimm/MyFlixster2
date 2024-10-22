package com.example.myflixster.models

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.util.Log
import com.example.myflixster.R

class MovieAdapter(
    private val movies: List<Movie>,
    private val baseImageUrl: String
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.movie_poster)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieDescription: TextView = view.findViewById(R.id.movie_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.movieTitle.text = movie.title
        holder.movieDescription.text = movie.overview

        // Construct the full image URL
        val posterUrl = movie.posterPath?.let { path ->
            "${baseImageUrl}w500$path"
        }

        // Log the poster path and full URL for debugging
        Log.d("MovieAdapter", "Movie: ${movie.title}")
        Log.d("MovieAdapter", "Poster path: ${movie.posterPath}")
        Log.d("MovieAdapter", "Base Image URL: $baseImageUrl")
        Log.d("MovieAdapter", "Full poster URL: $posterUrl")

        // Load the movie poster
        if (posterUrl != null) {
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error) // error drawable
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                    ): Boolean {
                        Log.e("MovieAdapter", "Image load failed for ${movie.title}: ${e?.message}", e)
                        return false // return false to indicate Glide should handle the error
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        Log.d("MovieAdapter", "Image loaded successfully for ${movie.title}")
                        return false // return false to allow Glide to update the ImageView
                    }
                })
                .into(holder.moviePoster)
        } else {
            // If posterUrl is null, load a placeholder or error image
            holder.moviePoster.setImageResource(R.drawable.error)
            Log.e("MovieAdapter", "No poster URL for ${movie.title}")
        }
    }

    override fun getItemCount(): Int = movies.size
}
