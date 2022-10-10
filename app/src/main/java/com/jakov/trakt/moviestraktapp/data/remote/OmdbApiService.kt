package com.jakov.trakt.moviestraktapp.data.remote

import com.jakov.trakt.moviestraktapp.data.remote.response.MovieDetailsResponse
import com.jakov.trakt.moviestraktapp.data.remote.response.MoviePosterResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

private const val ROOT_PATH_WILDCARD = "."
private const val IMDB_QUERY = "i"

interface OmdbApiService {

    @GET(ROOT_PATH_WILDCARD)
    suspend fun getMovieDetails(@Query(IMDB_QUERY) imdbId: String): MovieDetailsResponse

    @GET(ROOT_PATH_WILDCARD)
    fun getMoviePoster(@Query(IMDB_QUERY) imdbId: String): Single<MoviePosterResponse>
}