package com.jakov.trakt.moviestraktapp.ui.details

import androidx.lifecycle.viewModelScope
import com.jakov.trakt.moviestraktapp.domain.use_case.movie_details.GetMovieDetails
import com.jakov.trakt.moviestraktapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails
) : BaseViewModel<DetailsState>() {

    fun init(imdbId: String) {
        viewModelScope.launch {
            try {
                val movie = getMovieDetails(imdbId)

                setState(DetailsState.Loaded(movie))
            } catch (e: Exception) {
                setState(DetailsState.UnkownError)
                Timber.e(e)
            }
        }
    }
}