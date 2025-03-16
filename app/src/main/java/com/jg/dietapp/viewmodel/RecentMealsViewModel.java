package com.jg.dietapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.jg.dietapp.models.Meal;

import java.util.List;

public class RecentMealsViewModel extends ViewModel {
    private List<Meal> meals;

    public void setMeals(List<Meal> meals) {

        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
