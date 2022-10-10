package com.jakov.trakt.moviestraktapp.data.remote.response

import com.jakov.trakt.moviestraktapp.extensions.empty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    @Json(name = "Title")
    val title: String = String.empty,

    @Json(name = "Year")
    val year: String = String.empty,

    @Json(name = "Genre")
    val genre: String = String.empty,

    @Json(name = "Director")
    val director: String = String.empty,

    @Json(name = "Actors")
    val actors: String = String.empty,

    @Json(name = "Plot")
    val plot: String = String.empty,

    @Json(name = "Poster")
    val posterUrl: String = String.empty,
)
