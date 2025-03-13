package com.jg.dietapp.viewmodel;


import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.jg.dietapp.shared.SharedUserPrefs;
import com.jg.dietapp.shared.UserInput;

public class SharedUserPrefsViewModel extends ViewModel {

    private SharedUserPrefs sharedUserPrefs;
    public SharedUserPrefsViewModel(Application application) {
        sharedUserPrefs = new SharedUserPrefs(application);
    }

    public void saveUser(UserInput userInput) {
        sharedUserPrefs.saveUser(userInput);
    }

    public UserInput getUser() {
        return sharedUserPrefs.getUser();
    }
}

