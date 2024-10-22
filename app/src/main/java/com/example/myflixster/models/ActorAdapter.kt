package com.example.myflixster.models

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myflixster.ActorDetailsActivity
import com.example.myflixster.databinding.ItemActorBinding


class ActorAdapter(
    private val actors: List<Actor>,
    private val baseImageUrl: String
) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    inner class ActorViewHolder(private val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) {
            binding.tvActorName.text = actor.name

            // Load the actor's profile image using Glide
            actor.profilePath?.let { profilePath ->
                Glide.with(binding.root.context)
                    .load("https://image.tmdb.org/t/p/w500${actor.profilePath}")
                    .apply(RequestOptions().transform(RoundedCorners(16)))
                    .into(binding.ivActorProfile)
            }

            // Handle item clicks
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, ActorDetailsActivity::class.java)
                intent.putExtra("actor", actor) // Pass the actor object
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]
        holder.bind(actor)
    }

    override fun getItemCount(): Int = actors.size
}
