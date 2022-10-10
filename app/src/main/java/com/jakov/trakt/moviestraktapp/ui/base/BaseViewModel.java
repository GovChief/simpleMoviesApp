package com.jakov.trakt.moviestraktapp.ui.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel<State> extends ViewModel {

   private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData();

   public LiveData<State> stateLiveData = mutableStateLiveData;

   protected void setState(State state) {
      mutableStateLiveData.postValue(state);
   }
}
