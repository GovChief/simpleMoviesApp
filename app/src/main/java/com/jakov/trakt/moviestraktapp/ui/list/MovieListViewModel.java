package com.jakov.trakt.moviestraktapp.ui.list;

import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;
import com.jakov.trakt.moviestraktapp.domain.use_case.get_movies.GetMovies;
import com.jakov.trakt.moviestraktapp.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@HiltViewModel
class MovieListViewModel extends BaseViewModel<MovieListState> {

    private final GetMovies getMovies;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final List<UiMovie> movies = new ArrayList<>();
    private int nextPage = 1;
    private boolean isLoading = false;

    @Inject
    MovieListViewModel(GetMovies getMovies) {
        this.getMovies = getMovies;

        loadNextPage();
    }

    public void refresh() {
        if (isLoading && nextPage == 1) {
            return;
        }

        setState(new MovieListState.Loading());
        resetCalls();

        loadNextPage();
    }

    public void loadNextPage() {
        if (isLoading) {
            return;
        }
        isLoading = true;

        getMovies.execute(nextPage).subscribe(new SingleObserver<>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<UiMovie> uiMovies) {
                movies.addAll(uiMovies);
                setState(new MovieListState.Loaded(movies));
                isLoading = false;
                nextPage++;
            }

            @Override
            public void onError(Throwable e) {
                setState(new MovieListState.UnkownError());
                resetCalls();
                Timber.e(e);
            }
        });
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    private void resetCalls() {
        isLoading = false;
        nextPage = 1;
        movies.clear();
        compositeDisposable.clear();
    }
}
