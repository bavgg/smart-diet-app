package com.jg.dietapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectedMealsPrefs {
    private static final String PREF_NAME = "MealPrefs";
    private static final String SELECTED_MEALS_KEY = "selected_meals";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public SelectedMealsPrefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    // Save meal IDs using a Set<Integer> to prevent duplicates
    public void saveSelectedMeals(List<Integer> selectedMeals) {
        Set<String> mealIdSet = new HashSet<>();
        for (Integer id : selectedMeals) {
            mealIdSet.add(String.valueOf(id));
        }
        sharedPreferences.edit().putStringSet(SELECTED_MEALS_KEY, mealIdSet).apply();
    }

    // Retrieve saved meal IDs as a List<Integer>
    public List<Integer> getSelectedMeals() {
        Set<String> mealIdSet = sharedPreferences.getStringSet(SELECTED_MEALS_KEY, new HashSet<>());
        List<Integer> selectedMeals = new ArrayList<>();
        for (String id : mealIdSet) {
            selectedMeals.add(Integer.parseInt(id));
        }
        return selectedMeals;
    }

    // Clear saved selected meal IDs
    public void clearSelectedMeals() {
        sharedPreferences.edit().remove(SELECTED_MEALS_KEY).apply();
    }
}
