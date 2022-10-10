package com.jakov.trakt.moviestraktapp.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<State> extends Fragment {

    public abstract BaseViewModel<State> provideViewModel();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        provideViewModel().stateLiveData.observe(getViewLifecycleOwner(), this::handleState);
    }

    public abstract void handleState(State state);
}
