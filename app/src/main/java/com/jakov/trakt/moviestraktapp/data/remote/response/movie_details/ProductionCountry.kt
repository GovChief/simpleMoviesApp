package com.jakov.trakt.moviestraktapp.data.remote.response.movie_details

import com.jakov.trakt.moviestraktapp.extensions.empty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "name")
    var name: String = String.empty
)