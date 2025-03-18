package com.jg.dietapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jg.dietapp.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class RecentMealsViewModel extends ViewModel {
    private final MutableLiveData<List<Meal>> meals = new MutableLiveData<>(new ArrayList<>());

    public void setMeals(List<Meal> mealList) {
        meals.setValue(mealList); // Updates the LiveData
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    public void addMeal(Meal newMeal) {
        List<Meal> currentMeals = meals.getValue();
        if (currentMeals == null) {
            currentMeals = new ArrayList<>();
        }

        // Add new meal at the beginning
        currentMeals.add(0, newMeal);

        // Remove the last meal if the list has more than needed
        if (currentMeals.size() > 1) { // Adjust this if you want a fixed list size
            currentMeals.remove(currentMeals.size() - 1);
        }

        meals.setValue(currentMeals); // Update LiveData
    }
}
