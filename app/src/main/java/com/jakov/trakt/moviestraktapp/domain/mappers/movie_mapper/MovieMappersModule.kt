package com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MovieMappersModule {
    @Binds
    fun bindMovieDetailsMapper(mapper: MovieDetailsMapper): MovieMappers.MovieDetailsMapper

    @Binds
    fun bindMovieMapper(mapper: MovieMapper): MovieMappers.MovieMapper
}