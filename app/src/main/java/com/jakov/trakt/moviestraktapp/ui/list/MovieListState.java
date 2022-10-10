package com.jakov.trakt.moviestraktapp.ui.list;

import com.jakov.trakt.moviestraktapp.data.ui_model.UiMovie;

import java.util.List;

class MovieListState {

    static class Loading extends MovieListState {}

    static class Loaded extends MovieListState {

        public List<UiMovie> movies;

        public Loaded(List<UiMovie> movies) {
            this.movies = movies;
        }
    }

    static class UnkownError extends MovieListState {}
}
