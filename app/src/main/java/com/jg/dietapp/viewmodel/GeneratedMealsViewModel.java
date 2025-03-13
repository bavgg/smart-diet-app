package com.jg.dietapp.viewmodel;

import androidx.lifecycle.ViewModel;
import com.jg.dietapp.models.Meal;
import java.util.ArrayList;
import java.util.List;

public class GeneratedMealsViewModel extends ViewModel {

    private List<Meal>[] generatedMeals;
    private int baseCalories;

    public void setGeneratedMeals(List<Meal>[] meals) {
        this.generatedMeals = meals;
    }

    public List<Meal>[] getGeneratedMeals() {
        return generatedMeals;
    }

    public boolean isEmpty() {
        if (generatedMeals == null || generatedMeals.length == 0) {
            return true; // The array is null or empty
        }

        for (List<Meal> mealList : generatedMeals) {
            if (mealList != null && !mealList.isEmpty()) {
                return false; // At least one list has meals
            }
        }

        return true; // All lists are empty or null
    }

    public void setBaseCalories(int baseCalories) {
        this.baseCalories = baseCalories;

    }

    public int getBaseCalories() {
        return baseCalories;
    }
}
