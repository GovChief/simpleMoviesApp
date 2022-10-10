package com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper

import com.jakov.trakt.moviestraktapp.data.remote.response.MovieDetailsResponse
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovieDetails
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() : MovieMappers.MovieDetailsMapper {

    override fun mapToUiModel(networkModel: MovieDetailsResponse) = UiMovieDetails(
        title = networkModel.title,
        year = networkModel.year,
        genre = networkModel.genre,
        director = networkModel.director,
        actors = networkModel.actors,
        plot = networkModel.plot,
        posterUrl = networkModel.posterUrl,
    )
}