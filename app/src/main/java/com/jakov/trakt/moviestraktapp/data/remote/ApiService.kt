package com.jakov.trakt.moviestraktapp.data.remote

import com.jakov.trakt.moviestraktapp.data.remote.response.ListMoviesResponse
import com.jakov.trakt.moviestraktapp.data.remote.response.credits.Credits
import com.jakov.trakt.moviestraktapp.data.remote.response.movie_details.MovieDetailsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") pageNum: Int): Single<ListMoviesResponse>

    @GET("search/movie")
    fun searchMovies(@Query("page") pageNum: Int, @Query("query") query: String): Single<ListMoviesResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetailsResponse

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(@Path("id") id: Int): Credits
}