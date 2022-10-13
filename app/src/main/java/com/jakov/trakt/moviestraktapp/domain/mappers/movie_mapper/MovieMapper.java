package com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper;

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
    public UiMovie mapToUiModel(@NonNull MovieResponse networkModel) {
        return new UiMovie(
            networkModel.id,
            networkModel.title,
            "https://image.tmdb.org/t/p/w500" + networkModel.posterPath
        );
    }
}
