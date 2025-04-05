package com.jg.dietapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jg.dietapp.models.DietaryTrack;
import com.jg.dietapp.prefs.LoadPrefs;

public class CurrentNutritionViewModel extends AndroidViewModel {
    private final MutableLiveData<Integer> kcal = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> protein = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> carbs = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> fat = new MutableLiveData<>(0);

    private final LoadPrefs loadPrefs;

    public CurrentNutritionViewModel(@NonNull Application application) {
        super(application);
        loadPrefs = new LoadPrefs(application);
        DietaryTrack currentDietaryTrack = loadPrefs.getCurrentDietaryTrack();
        kcal.setValue(currentDietaryTrack.getCalories());
        protein.setValue(currentDietaryTrack.getProtein());
        carbs.setValue(currentDietaryTrack.getCarbs());
        fat.setValue(currentDietaryTrack.getFat());
    }

    public LiveData<Integer> getCurrentKcal() {
        return kcal;
    }

    public LiveData<Integer> getCurrentProtein() {
        return protein;
    }

    public LiveData<Integer> getCurrentCarbs() {
        return carbs;
    }

    public LiveData<Integer> getCurrentFat() {
        return fat;
    }



    public void clearNutritionData() {
        kcal.setValue(0);
        protein.setValue(0);
        carbs.setValue(0);
        fat.setValue(0);

        DietaryTrack currentDietaryTrack = new DietaryTrack(0, 0, 0, 0);
        loadPrefs.saveCurrentDietaryTrack(currentDietaryTrack);
    }

    public void updateNutrition(int deltaKcal, int deltaProtein, int deltaCarbs, int deltaFat) {
        int newKcal = (kcal.getValue() != null ? kcal.getValue() : 0) + deltaKcal;
        int newProtein = (protein.getValue() != null ? protein.getValue() : 0) + deltaProtein;
        int newCarbs = (carbs.getValue() != null ? carbs.getValue() : 0) + deltaCarbs;
        int newFat = (fat.getValue() != null ? fat.getValue() : 0) + deltaFat;

        kcal.setValue(newKcal);
        protein.setValue(newProtein);
        carbs.setValue(newCarbs);
        fat.setValue(newFat);

        DietaryTrack currentDietaryTrack = new DietaryTrack(newKcal, newProtein, newCarbs, newFat);

        loadPrefs.saveCurrentDietaryTrack(currentDietaryTrack);
    }


}
