package com.jg.dietapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.jg.dietapp.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class SelectedMealsViewModel extends ViewModel {
//    private boolean isGenerated = false;

    private final List<Integer> selectedMeals = new ArrayList<>();

    public void setSelectedMeals(List<Integer> meals) {

    }



    public List<Integer> getSelectedMeals() {
        return selectedMeals;
    }


}
