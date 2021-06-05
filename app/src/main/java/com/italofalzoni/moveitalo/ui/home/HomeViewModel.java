package com.italofalzoni.moveitalo.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mUsername;

    public HomeViewModel() {
        mUsername = new MutableLiveData<>();
        mUsername.setValue("This is a username");
    }

    public LiveData<String> getText() {
        return mUsername;
    }
}