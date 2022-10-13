package com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper

import com.jakov.trakt.moviestraktapp.data.remote.response.credits.Credits
import com.jakov.trakt.moviestraktapp.data.remote.response.movie_details.MovieDetailsResponse
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovieDetails
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val ACTING_DEPARTMENT = "Acting"
private const val TAKE_FIRST_INFO = 3
private const val SEPARATOR = ", "

class MovieDetailsMapper @Inject constructor() : MovieMappers.MovieDetailsMapper {

    override fun mapToUiModel(networkModel: MovieDetailsResponse, creditsNetworkModel: Credits) = UiMovieDetails(
        title = networkModel.title,
        releaseDate = networkModel.releaseDate,
        genres = networkModel.genres.joinToString(separator = SEPARATOR) { it.name },
        actors = creditsNetworkModel.cast
            .filter { it.knownForDepartment == ACTING_DEPARTMENT }
            .take(TAKE_FIRST_INFO)
            .joinToString(SEPARATOR) { it.name },
        plot = networkModel.overview,
        posterUrl = "https://image.tmdb.org/t/p/w500" + networkModel.posterPath,
        countries = networkModel.productionCountries.take(TAKE_FIRST_INFO).joinToString(SEPARATOR) { it.name }
    )
}