package com.jakov.trakt.moviestraktapp.data.remote.response;

import com.squareup.moshi.Json;

public class MoviesResponse {

    @Json(name = "movie")
    public MovieResponse movie;
}

