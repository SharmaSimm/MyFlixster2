package com.example.myflixster.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Actor(

// This represents individual actor data

@SerializedName("id") val id: Int,
@SerializedName("name") val name: String,
@SerializedName("profile_path") val profilePath: String?, // Nullable because sometimes there might be no profile image
@SerializedName("known_for") val knownFor: List<KnownFor>
) : Serializable // Implement Serializable

data class KnownFor(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("name") val name: String?,  // For TV shows
    @SerializedName("poster_path") val posterPath: String?
) : Serializable // Implement Serializable

