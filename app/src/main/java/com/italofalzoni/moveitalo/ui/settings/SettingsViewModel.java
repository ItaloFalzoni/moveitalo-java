package com.italofalzoni.moveitalo.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mUsername;

    public SettingsViewModel() {
        mUsername = new MutableLiveData<>();
        mUsername.setValue("This is a username");
    }

    public LiveData<String> getText() {
        return mUsername;
    }
}