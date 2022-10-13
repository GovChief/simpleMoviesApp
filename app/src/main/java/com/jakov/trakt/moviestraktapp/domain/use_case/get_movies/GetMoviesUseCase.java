package com.jakov.trakt.moviestraktapp.domain.use_case.get_movies;

import com.jakov.trakt.moviestraktapp.data.remote.ApiService;
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;
import com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper.MovieMappers;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class GetMoviesUseCase implements GetMovies {

    private final ApiService apiService;
    private final MovieMappers.MovieMapper movieMapper;

    @Inject
    public GetMoviesUseCase(
        ApiService apiService,
        MovieMappers.MovieMapper movieMapper
    ) {
        this.apiService = apiService;
        this.movieMapper = movieMapper;
    }

    @Override
    public Single<List<UiMovie>> execute(int pageNum) {
        return apiService.getPopularMovies(pageNum)
            .map(movies -> movies.results.stream().map(movieMapper::mapToUiModel).collect(Collectors.toList()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
