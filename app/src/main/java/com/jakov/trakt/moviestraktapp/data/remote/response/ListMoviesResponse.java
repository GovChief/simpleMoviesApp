package com.jakov.trakt.moviestraktapp.data.remote.response;

import com.squareup.moshi.Json;

import java.util.List;

public class ListMoviesResponse {

    @Json(name = "page")
    public int page;

    @Json(name = "results")
    public List<MovieResponse> results;
}
