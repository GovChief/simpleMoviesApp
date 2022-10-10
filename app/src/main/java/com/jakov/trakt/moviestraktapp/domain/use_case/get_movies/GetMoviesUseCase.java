package com.jakov.trakt.moviestraktapp.domain.use_case.get_movies;

import com.jakov.trakt.moviestraktapp.data.remote.OmdbApiService;
import com.jakov.trakt.moviestraktapp.data.remote.TraktApiService;
import com.jakov.trakt.moviestraktapp.data.remote.response.MoviePosterResponse;
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;
import com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper.MovieMappers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class GetMoviesUseCase implements GetMovies {

    private final TraktApiService traktApiService;
    private final OmdbApiService omdbApiService;
    private final MovieMappers.MovieMapper movieMapper;

    @Inject
    public GetMoviesUseCase(
        TraktApiService apiService,
        OmdbApiService omdbApiService,
        MovieMappers.MovieMapper movieMapper
    ) {
        this.traktApiService = apiService;
        this.omdbApiService = omdbApiService;
        this.movieMapper = movieMapper;
    }

    @Override
    public Single<List<UiMovie>> execute(int pageNum) {
        return traktApiService.getMovies(pageNum)
            .flattenAsFlowable(moviesResponses -> moviesResponses)
            .map(moviesResponse ->
                omdbApiService.getMoviePoster(moviesResponse.movie.ids.imdb)
                    .onErrorReturn(throwable -> new MoviePosterResponse())
                    .map(moviePosterResponse -> movieMapper.mapToUiModel(moviesResponse.movie, moviePosterResponse)
                    ).blockingGet()
            )
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
