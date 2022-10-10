package com.jakov.trakt.moviestraktapp.data.ui_model;

public class UiMovie {

    public String title;
    public String imageCover;
    public String imdbId;

    public UiMovie(String title, String imageCover, String imdbId) {
        this.title = title;
        this.imageCover = imageCover;
        this.imdbId = imdbId;
    }
}
