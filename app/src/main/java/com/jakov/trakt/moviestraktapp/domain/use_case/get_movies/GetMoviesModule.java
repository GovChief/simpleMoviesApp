package com.jakov.trakt.moviestraktapp.domain.use_case.get_movies;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
interface GetMoviesModule {

    @Binds
    GetMovies BindGetMoviesUseCase(GetMoviesUseCase useCase);
}
