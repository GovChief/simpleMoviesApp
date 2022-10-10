package com.jakov.trakt.moviestraktapp.domain.use_case.movie_details

import com.jakov.trakt.moviestraktapp.data.remote.OmdbApiService
import com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper.MovieMappers
import com.jakov.trakt.moviestraktapp.shared.dispatcher.Dispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val omdbApiService: OmdbApiService,
    private val movieDetailsMappers: MovieMappers.MovieDetailsMapper,
    private val dispatchers: Dispatcher
) : GetMovieDetails {

    override suspend fun invoke(imdbId: String) = withContext(dispatchers.io) {
        val movieDetails = omdbApiService.getMovieDetails(imdbId)
        movieDetailsMappers.mapToUiModel(movieDetails)
    }
}