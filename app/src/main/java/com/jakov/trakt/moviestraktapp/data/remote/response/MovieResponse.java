package com.jakov.trakt.moviestraktapp.data.remote.response;

import com.squareup.moshi.Json;

public class MovieResponse {

    @Json(name = "title")
    public String title;

    @Json(name = "ids")
    public IdsResponse ids;
}
