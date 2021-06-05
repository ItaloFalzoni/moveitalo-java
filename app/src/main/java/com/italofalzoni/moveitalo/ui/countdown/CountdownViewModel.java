package com.italofalzoni.moveitalo.ui.countdown;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CountdownViewModel extends ViewModel {

    private MutableLiveData<String> mUsername;

    public CountdownViewModel() {
        mUsername = new MutableLiveData<>();
        mUsername.setValue("This is a username");
    }

    public LiveData<String> getText() {
        return mUsername;
    }
}