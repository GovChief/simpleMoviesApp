package com.jakov.trakt.moviestraktapp.data.remote.response.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Credits(
    @Json(name = "cast")
    public val cast: List<CastMember>
)