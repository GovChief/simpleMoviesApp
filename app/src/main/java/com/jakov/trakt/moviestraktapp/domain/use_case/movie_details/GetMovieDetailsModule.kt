package com.jakov.trakt.moviestraktapp.domain.use_case.movie_details

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GetMovieDetailsModule {
    @Binds
    fun bindGetMovieDetailsUseCase(useCase: GetMovieDetailsUseCase): GetMovieDetails
}