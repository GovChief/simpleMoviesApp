package com.jakov.trakt.moviestraktapp.data.remote.response

import com.jakov.trakt.moviestraktapp.extensions.empty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviePosterResponse(
    @Json(name = "Poster")
    val posterUrl: String = String.empty,
)
