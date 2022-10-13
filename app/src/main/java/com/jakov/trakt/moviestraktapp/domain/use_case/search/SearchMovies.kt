package com.jakov.trakt.moviestraktapp.domain.use_case.search

import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie
import io.reactivex.Single

interface SearchMovies {
    fun invoke(searchText: String, pageNum: Int): Single<List<UiMovie>>
}