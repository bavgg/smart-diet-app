package com.jg.dietapp.viewmodel;

import androidx.lifecycle.ViewModel;
import com.jg.dietapp.models.Meal;

import java.util.List;

public class GeneratedPlanViewModel extends ViewModel {

    private List<Meal> breakfastMeals;
    private List<Meal> lunchMeals;
    private List<Meal> dinnerMeals;
    private int baseCalories;

    public void setBreakfastMeals(List<Meal> breakfastMeals) {
        this.breakfastMeals = breakfastMeals;
    }

    public List<Meal> getBreakfastMeals() {
        return breakfastMeals;
    }

    public void setLunchMeals(List<Meal> lunchMeals) {
        this.lunchMeals = lunchMeals;
    }

    public List<Meal> getLunchMeals() {
        return lunchMeals;
    }

    public void setDinnerMeals(List<Meal> dinnerMeals) {
        this.dinnerMeals = dinnerMeals;
    }

    public List<Meal> getDinnerMeals() {
        return dinnerMeals;
    }

//    public boolean isEmpty() {
//        if (generatedMeals == null || generatedMeals.length == 0) {
//            return true; // The array is null or empty
//        }
//
//        for (List<Meal> mealList : generatedMeals) {
//            if (mealList != null && !mealList.isEmpty()) {
//                return false; // At least one list has meals
//            }
//        }
//
//        return true; // All lists are empty or null
//    }

    public void setBaseCalories(int baseCalories) {
        this.baseCalories = baseCalories;

    }

    public int getBaseCalories() {
        return baseCalories;
    }
}
