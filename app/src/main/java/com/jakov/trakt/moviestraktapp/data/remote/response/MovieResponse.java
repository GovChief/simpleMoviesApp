package com.jakov.trakt.moviestraktapp.data.remote.response;

import com.squareup.moshi.Json;

public class MovieResponse {

    @Json(name = "id")
    public int id;

    @Json(name = "title")
    public String title;

    @Json(name = "poster_path")
    public String posterPath;
}
