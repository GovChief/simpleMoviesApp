package com.jakov.trakt.moviestraktapp.domain.use_case.get_movies;

import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;

import java.util.List;

import io.reactivex.Single;

public interface GetMovies {
    Single<List<UiMovie>> execute(int pageNum);
}
