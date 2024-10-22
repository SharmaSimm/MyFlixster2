package com.example.myflixster.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// This represents the response from the /person/popular endpoint
data class ActorResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val actors: List<Actor>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : Serializable // Implement Serializable
//
//// This represents individual actor data
//data class Actor(
//    @SerializedName("id") val id: Int,
//    @SerializedName("name") val name: String,
//    @SerializedName("profile_path") val profilePath: String?, // Nullable because sometimes there might be no profile image
//    @SerializedName("known_for") val knownFor: List<KnownFor>
//) : Serializable // Implement Serializable
//
//data class KnownFor(
//    @SerializedName("id") val id: Int,
//    @SerializedName("title") val title: String?,
//    @SerializedName("name") val name: String?,  // For TV shows
//    @SerializedName("poster_path") val posterPath: String?
//) : Serializable // Implement Serializable
