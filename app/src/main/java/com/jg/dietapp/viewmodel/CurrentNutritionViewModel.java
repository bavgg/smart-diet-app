package com.jg.dietapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jg.dietapp.prefs.FirebaseDataPrefs;

public class CurrentNutritionViewModel extends AndroidViewModel {
    private final MutableLiveData<Integer> kcal = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> protein = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> carbs = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> fat = new MutableLiveData<>(0);

    private final FirebaseDataPrefs firebaseDataPrefs;

    public CurrentNutritionViewModel(@NonNull Application application) {
        super(application);
        firebaseDataPrefs = new FirebaseDataPrefs(application);
        kcal.setValue(firebaseDataPrefs.getKcal());
        protein.setValue(firebaseDataPrefs.getProtein());
        carbs.setValue(firebaseDataPrefs.getCarbs());
        fat.setValue(firebaseDataPrefs.getFat());
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

        firebaseDataPrefs.saveNutritionData(newKcal, newProtein, newCarbs, newFat);
    }


}
