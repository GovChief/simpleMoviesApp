package com.jakov.trakt.moviestraktapp.ui.details

import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovieDetails

sealed class DetailsState {
    object Loading : DetailsState()
    data class Loaded(val movie: UiMovieDetails) : DetailsState()
    object UnkownError : DetailsState()
}