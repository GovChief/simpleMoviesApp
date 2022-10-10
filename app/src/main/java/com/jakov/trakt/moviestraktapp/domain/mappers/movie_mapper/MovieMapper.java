package com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper;

import com.jakov.trakt.moviestraktapp.data.remote.response.MoviePosterResponse;
import com.jakov.trakt.moviestraktapp.data.remote.response.MovieResponse;
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class MovieMapper implements MovieMappers.MovieMapper {

    @Inject
    public MovieMapper() {
    }

    @NonNull
    @Override
    public UiMovie mapToUiModel(@NonNull MovieResponse movieNetworkModel, @NonNull MoviePosterResponse posterNetworkModel) {
        return new UiMovie(
            movieNetworkModel.title,
            posterNetworkModel.getPosterUrl(),
            movieNetworkModel.ids.imdb);
    }
}
