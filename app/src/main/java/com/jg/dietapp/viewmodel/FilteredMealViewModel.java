package com.jg.dietapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jg.dietapp.models.Meal;

import java.util.List;

public class FilteredMealViewModel extends ViewModel {
    private MutableLiveData<List<Meal>> meals = new MutableLiveData<>();

    public void setMeals(List<Meal> mealList) {
        meals.setValue(mealList);
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }
}
