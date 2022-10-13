package com.jakov.trakt.moviestraktapp.ui.list;

import android.util.Log;

import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;
import com.jakov.trakt.moviestraktapp.domain.use_case.get_movies.GetMovies;
import com.jakov.trakt.moviestraktapp.domain.use_case.search.SearchMovies;
import com.jakov.trakt.moviestraktapp.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.observable.ObservableDoOnEach;
import io.reactivex.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

@HiltViewModel
class MovieListViewModel extends BaseViewModel<MovieListState> {

    private static final int MIN_SEARCH_TEXT_LENGTH = 3;
    private static final int SEARCH_DEBOUNCE = 500;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Disposable searchDisposable;
    private final GetMovies getMovies;
    private final SearchMovies searchMovies;

    private final List<UiMovie> movies = new ArrayList<>();
    private int nextPage = 1;
    private boolean isLoading = false;
    private String previousSearchText = "";

    private final PublishSubject<String> publishSubject = PublishSubject.create();

    @Inject
    MovieListViewModel(GetMovies getMovies, SearchMovies searchMovies) {
        this.getMovies = getMovies;
        this.searchMovies = searchMovies;

        loadNextPopularPage();

        searchDisposable = publishSubject.debounce(SEARCH_DEBOUNCE, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(searchText -> {
                    if (searchText.equals(previousSearchText)) {
                        return;
                    }
                    if (searchText.length() >= MIN_SEARCH_TEXT_LENGTH) {
                        this.previousSearchText = searchText;
                        resetCalls();
                        setState(new MovieListState.Loading());
                        loadNextSearchPage();
                    } else if (previousSearchText.length() >= MIN_SEARCH_TEXT_LENGTH) {
                        this.previousSearchText = searchText;
                        resetCalls();
                        setState(new MovieListState.Loading());
                        loadNextPopularPage();
                    }
                }
            );
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

        if (previousSearchText.length() >= MIN_SEARCH_TEXT_LENGTH) {
            loadNextSearchPage();
        } else {
            loadNextPopularPage();
        }
    }

    private void loadNextPopularPage() {
        getMovies.execute(nextPage).subscribe(provideSingleObserver());
    }

    private void loadNextSearchPage() {
        searchMovies.invoke(previousSearchText, nextPage).subscribe(provideSingleObserver());
    }

    private void resetCalls() {
        isLoading = false;
        nextPage = 1;
        movies.clear();
        compositeDisposable.clear();
    }

    public void setSearchText(String searchText) {
        publishSubject.onNext(searchText);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        searchDisposable.dispose();
        super.onCleared();
    }

    private SingleObserver<List<UiMovie>> provideSingleObserver() {
        return new SingleObserver<>() {
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
        };
    }
}
