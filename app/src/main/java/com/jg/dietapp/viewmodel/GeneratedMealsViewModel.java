package com.jg.dietapp.viewmodel;

import androidx.lifecycle.ViewModel;
import com.jg.dietapp.models.Meal;
import java.util.ArrayList;
import java.util.List;

public class GeneratedMealsViewModel extends ViewModel {

    private List<Meal>[] selectedMeals;

    public void setMeals(List<Meal>[] meals) {
        this.selectedMeals = meals;
    }

    public List<Meal>[] getSelectedMeals() {
        return selectedMeals;
    }

    public boolean isEmpty() {
        if (selectedMeals == null || selectedMeals.length == 0) {
            return true; // The array is null or empty
        }

        for (List<Meal> mealList : selectedMeals) {
            if (mealList != null && !mealList.isEmpty()) {
                return false; // At least one list has meals
            }
        }

        return true; // All lists are empty or null
    }
}
