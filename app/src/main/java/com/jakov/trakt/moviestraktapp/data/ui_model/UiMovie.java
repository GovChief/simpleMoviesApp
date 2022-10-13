package com.jakov.trakt.moviestraktapp.data.ui_model;

public class UiMovie {

    public int id;
    public String title;
    public String imageCover;

    public UiMovie(int id, String title, String imageCover) {
        this.id = id;
        this.title = title;
        this.imageCover = imageCover;
    }
}
