package com.jg.dietapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private int cmValue = 165;


    public void setCmValue(int cm) {
        cmValue = cm;
    }

    public int getCmValue() {
        return cmValue;
    }




}
