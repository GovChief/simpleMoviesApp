package com.jakov.trakt.moviestraktapp.domain.use_case.movie_details

import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovieDetails

interface GetMovieDetails {
    suspend operator fun invoke(imdbId: String): UiMovieDetails
}