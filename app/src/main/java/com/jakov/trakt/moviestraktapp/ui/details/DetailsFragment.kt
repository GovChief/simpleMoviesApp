package com.jakov.trakt.moviestraktapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.jakov.trakt.moviestraktapp.R
import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovieDetails
import com.jakov.trakt.moviestraktapp.extensions.empty
import com.jakov.trakt.moviestraktapp.ui.base.BaseFragment
import com.jakov.trakt.moviestraktapp.ui.base.DoNothing
import dagger.hilt.android.AndroidEntryPoint
import okio.IOException

@AndroidEntryPoint
class DetailsScreen : BaseFragment<DetailsState>() {

    companion object {
        const val ID_EXTRA = "ID_EXTRA"
    }

    private val viewModel: DetailsViewModel by viewModels()

    override fun provideViewModel() = viewModel

    override fun handleState(state: DetailsState?) = DoNothing

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppCompatTheme {
                    val state by viewModel.stateLiveData.observeAsState()

                    HandleState(state = state, retry = ::initViewModel)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val id = arguments?.getInt(ID_EXTRA) ?: throw IOException()
        viewModel.init(id)
    }
}

@Composable
private fun HandleState(
    state: DetailsState?,
    retry: () -> Unit
) {
    when (state) {
        is DetailsState.Loaded -> Content(state.movie)
        DetailsState.Loading -> Loading()
        is DetailsState.UnkownError -> Error(retry)
        else -> DoNothing
    }
}

@Composable
private fun Loading() {
    CircularProgressIndicator()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Content(movie: UiMovieDetails) {
    LazyColumn {
        item {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, dimensionResource(id = R.dimen.movie_details_poster_max_height))
                    .background(color = Color.Black),
                model = movie.posterUrl,
                contentDescription = String.empty
            )
        }
        item {
            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing1x)),
                text = movie.title,
                style = MaterialTheme.typography.h2
            )
        }
        item {
            Text(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.spacing2x))
                    .padding(top = dimensionResource(id = R.dimen.spacing1x)),
                text = movie.plot
            )
        }
        item {
            Row(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.spacing2x))
                    .padding(horizontal = dimensionResource(id = R.dimen.spacing2x))
                    .fillMaxWidth(),
            ) {
                Info(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.release_date),
                    value = movie.releaseDate
                )
                Info(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.genre),
                    value = movie.genres
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.spacing2x))
                    .fillMaxWidth(),
            ) {
                Info(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.actors),
                    value = movie.actors
                )
                Info(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.countries),
                    value = movie.countries
                )
            }
        }
    }
}

@Composable
private fun Error(retry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing2x)),
            text = stringResource(R.string.something_went_wrong_with_retry),
            style = MaterialTheme.typography.body1
        )
        Button(onClick = retry) {
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
private fun Info(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.spacing1x)),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.caption
        )
        Text(text = value)
    }
}
