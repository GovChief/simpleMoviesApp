package com.jakov.trakt.moviestraktapp.data.remote.response.movie_details

import com.jakov.trakt.moviestraktapp.extensions.empty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDetailsResponse(

    @Json(name = "title")
    var title: String = String.empty,

    @Json(name = "release_date")
    var releaseDate: String = String.empty,

    @Json(name = "overview")
    var overview: String = String.empty,

    @Json(name = "genres")
    var genres: List<GenreResponse> = emptyList(),

    @Json(name = "poster_path")
    var posterPath: String = String.empty,

    @Json(name = "production_countries")
    var productionCountries: List<ProductionCountry> = emptyList(),
)
