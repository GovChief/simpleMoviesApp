package com.jakov.trakt.moviestraktapp.domain.use_case.movie_details

import com.jakov.trakt.moviestraktapp.data.remote.ApiService
import com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper.MovieMappers
import com.jakov.trakt.moviestraktapp.shared.dispatcher.Dispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val apiService: ApiService,
    private val movieDetailsMappers: MovieMappers.MovieDetailsMapper,
    private val dispatchers: Dispatcher
) : GetMovieDetails {

    override suspend fun invoke(id: Int) = withContext(dispatchers.io) {
        val movieDetails = apiService.getMovieDetails(id)
        val movieCredits = apiService.getMovieCredits(id)
        movieDetailsMappers.mapToUiModel(movieDetails, movieCredits)
    }
}