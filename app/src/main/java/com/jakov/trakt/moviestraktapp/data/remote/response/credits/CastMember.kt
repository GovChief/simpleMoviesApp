package com.jakov.trakt.moviestraktapp.data.remote.response.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastMember(
    @Json(name = "known_for_department")
    public val knownForDepartment: String,

    @Json(name = "name")
    public val name: String
)
