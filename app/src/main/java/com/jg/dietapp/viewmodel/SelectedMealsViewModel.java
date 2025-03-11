package com.jg.dietapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.jg.dietapp.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class SelectedMealsViewModel extends ViewModel {
    private boolean isGenerated = false;

    private final List<Meal> selectedMeals = new ArrayList<>();

    public void setMeals(List<Meal> meals) {
        selectedMeals.clear();
        selectedMeals.addAll(meals);
    }

    public void setIsGenerated(boolean isGenerated) {
        this.isGenerated = isGenerated;
    }

    public boolean getIsGenerated() {
        return isGenerated;
    }

    public List<Meal> getSelectedMeals() {
        return selectedMeals;
    }

    public void clearMeals() {
        selectedMeals.clear();
    }
}
