package com.jg.dietapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NutritionViewModel extends ViewModel {
    private final MutableLiveData<Integer> kcal = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> protein = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> carbs = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> fat = new MutableLiveData<>(0);

    public LiveData<Integer> getKcal() {
        return kcal;
    }

    public LiveData<Integer> getProtein() {
        return protein;
    }

    public LiveData<Integer> getCarbs() {
        return carbs;
    }

    public LiveData<Integer> getFat() {
        return fat;
    }

    public void updateNutrition(int deltaKcal, int deltaProtein, int deltaCarbs, int deltaFat) {
        kcal.setValue((kcal.getValue() != null ? kcal.getValue() : 0) + deltaKcal);
        protein.setValue((protein.getValue() != null ? protein.getValue() : 0) + deltaProtein);
        carbs.setValue((carbs.getValue() != null ? carbs.getValue() : 0) + deltaCarbs);
        fat.setValue((fat.getValue() != null ? fat.getValue() : 0) + deltaFat);
    }

}
