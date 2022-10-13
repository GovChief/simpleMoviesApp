package com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper

import com.jakov.trakt.moviestraktapp.data.remote.response.movie_details.MovieDetailsResponse
import com.jakov.trakt.moviestraktapp.data.remote.response.MoviePosterResponse
import com.jakov.trakt.moviestraktapp.data.remote.response.MovieResponse
import com.jakov.trakt.moviestraktapp.data.remote.response.credits.Credits
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovieDetails

interface MovieMappers {
    interface MovieDetailsMapper {
        fun mapToUiModel(networkModel: MovieDetailsResponse, creditsNetworkModel: Credits): UiMovieDetails
    }

    interface MovieMapper {
        fun mapToUiModel(movieNetworkModel: MovieResponse): UiMovie
    }
}