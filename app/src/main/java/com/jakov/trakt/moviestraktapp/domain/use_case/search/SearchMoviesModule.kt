package com.jakov.trakt.moviestraktapp.domain.use_case.search

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SearchMoviesModule {
    @Binds
    fun bindSearchMoviesUseCase(useCase: SearchMoviesUseCase): SearchMovies
}