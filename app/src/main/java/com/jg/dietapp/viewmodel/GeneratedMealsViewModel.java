package com.jg.dietapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.jg.dietapp.models.Meal;

import java.util.List;

public class GeneratedMealsViewModel extends ViewModel {

    private final MutableLiveData<List<Meal>> breakfastMeals = new MutableLiveData<>();
    private final MutableLiveData<List<Meal>> lunchMeals = new MutableLiveData<>();
    private final MutableLiveData<List<Meal>> dinnerMeals = new MutableLiveData<>();
    private final MutableLiveData<Integer> baseCalories = new MutableLiveData<>();

    public void setBreakfastMeals(List<Meal> meals) {
        breakfastMeals.setValue(meals);
    }

    public LiveData<List<Meal>> getBreakfastMeals() {
        return breakfastMeals;
    }

    public void setLunchMeals(List<Meal> meals) {
        lunchMeals.setValue(meals);
    }

    public LiveData<List<Meal>> getLunchMeals() {
        return lunchMeals;
    }

    public void setDinnerMeals(List<Meal> meals) {
        dinnerMeals.setValue(meals);
    }

    public LiveData<List<Meal>> getDinnerMeals() {
        return dinnerMeals;
    }

    public void setBaseCalories(int calories) {
        baseCalories.setValue(calories);
    }

    public LiveData<Integer> getBaseCalories() {
        return baseCalories;
    }
}
