package com.jg.dietapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jg.dietapp.prefs.GoalNutrientsPrefs;

public class NutritionViewModel extends AndroidViewModel {
    private final MutableLiveData<Integer> kcal = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> protein = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> carbs = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> fat = new MutableLiveData<>(0);

    private final GoalNutrientsPrefs sharedPrefsNutrients;

    public NutritionViewModel(@NonNull Application application) {
        super(application);
        sharedPrefsNutrients = new GoalNutrientsPrefs(application);
        loadNutritionData();
    }

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

//    public void updateNutrition(int deltaKcal, int deltaProtein, int deltaCarbs, int deltaFat) {
//        kcal.setValue((kcal.getValue() != null ? kcal.getValue() : 0) + deltaKcal);
//        protein.setValue((protein.getValue() != null ? protein.getValue() : 0) + deltaProtein);
//        carbs.setValue((carbs.getValue() != null ? carbs.getValue() : 0) + deltaCarbs);
//        fat.setValue((fat.getValue() != null ? fat.getValue() : 0) + deltaFat);
//    }

    public void updateNutrition(int deltaKcal, int deltaProtein, int deltaCarbs, int deltaFat) {
        int newKcal = (kcal.getValue() != null ? kcal.getValue() : 0) + deltaKcal;
        int newProtein = (protein.getValue() != null ? protein.getValue() : 0) + deltaProtein;
        int newCarbs = (carbs.getValue() != null ? carbs.getValue() : 0) + deltaCarbs;
        int newFat = (fat.getValue() != null ? fat.getValue() : 0) + deltaFat;

        kcal.setValue(newKcal);
        protein.setValue(newProtein);
        carbs.setValue(newCarbs);
        fat.setValue(newFat);

        sharedPrefsNutrients.saveNutritionData(newKcal, newProtein, newCarbs, newFat);
    }

    private void loadNutritionData() {
        kcal.setValue(sharedPrefsNutrients.getKcal());
        protein.setValue(sharedPrefsNutrients.getProtein());
        carbs.setValue(sharedPrefsNutrients.getCarbs());
        fat.setValue(sharedPrefsNutrients.getFat());
    }
}
