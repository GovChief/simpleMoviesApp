package com.jakov.trakt.moviestraktapp.shared;

import android.text.Editable;
import android.text.TextWatcher;

public interface OnTextChangeListener extends TextWatcher {

    void onTextChange(String text);

    @Override
    default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    default void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onTextChange(charSequence.toString());
    }

    @Override
    default void afterTextChanged(Editable editable) {

    }
}
