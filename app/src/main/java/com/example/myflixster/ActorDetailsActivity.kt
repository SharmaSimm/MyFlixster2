package com.example.myflixster

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myflixster.models.Actor

// In ActorDetailsActivity.kt
class ActorDetailsActivity : AppCompatActivity() {
    private lateinit var ivActorImage: ImageView
    private lateinit var tvActorName: TextView
    private lateinit var tvKnownFor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_details)

        ivActorImage = findViewById(R.id.ivActorImage)
        tvActorName = findViewById(R.id.tvActorName)
        tvKnownFor = findViewById(R.id.tvKnownFor)

        // Retrieve the actor object
        val actor = intent.getSerializableExtra("actor") as? Actor
        Log.d("ActorDetailsActivity", "Received actor: $actor")

        actor?.let {
            tvActorName.text = it.name
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${it.profilePath}")
                .into(ivActorImage)

            // Join known for titles
            tvKnownFor.text = it.knownFor.joinToString {
                it.title ?: it.name ?: ""
            }
        }
    }
}
