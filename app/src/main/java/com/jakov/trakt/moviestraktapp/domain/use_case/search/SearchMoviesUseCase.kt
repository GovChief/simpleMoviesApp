package com.jakov.trakt.moviestraktapp.domain.use_case.search

import com.jakov.trakt.moviestraktapp.data.remote.ApiService
import com.jakov.trakt.moviestraktapp.domain.mappers.movie_mapper.MovieMappers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieMappers.MovieMapper,
) : SearchMovies {

    override fun invoke(searchText: String, pageNum: Int) =
        apiService.searchMovies(pageNum, searchText)
            .map { movies -> movies.results.map { movie -> movieMapper.mapToUiModel(movie) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}