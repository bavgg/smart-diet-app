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

    private double lbs = 154;


    public void setLbs(int lbs, int lbsDecimal) {
        this.lbs = lbs + (lbsDecimal / 10.0f);
    }

    public double getLbs() {
        return lbs;
    }

}
