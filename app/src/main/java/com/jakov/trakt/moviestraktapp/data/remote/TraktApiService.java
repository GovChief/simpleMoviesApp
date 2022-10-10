package com.jakov.trakt.moviestraktapp.data.remote;

import com.jakov.trakt.moviestraktapp.data.remote.response.MoviesResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TraktApiService {

    @GET("trending")
    Single<List<MoviesResponse>> getMovies(@Query("page") int pageNum);
}
