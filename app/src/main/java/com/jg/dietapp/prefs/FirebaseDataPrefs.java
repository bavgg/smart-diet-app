package com.jg.dietapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jg.dietapp.models.Exercise;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirebaseDataPrefs {

    private static final String PREFS_NAME = "firebase_data_prefs";
    private static final String KEY_KCAL = "kcal";
    private static final String KEY_PROTEIN = "protein";
    private static final String KEY_CARBS = "carbs";
    private static final String KEY_FAT = "fat";
    private static final String KEY_USER_INPUTS = "user_inputs";
    private static final String KEY_SELECTED_MEALS_ID = "selected_meals_id";
    private static final String KEY_BREAKFAST_MEALS = "breakfast_meals";
    private static final String KEY_LUNCH_MEALS = "lunch_meals";
    private static final String KEY_DINNER_MEALS = "dinner_meals";
    private static final String KEY_BASE_CALORIES = "base_calories";
    private static final String KEY_EXERCISES = "exercises";

    private final SharedPreferences sharedPreferences;

    public FirebaseDataPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveNutritionData(int kcal, int protein, int carbs, int fat) {
        sharedPreferences.edit()
                .putInt(KEY_KCAL, kcal)
                .putInt(KEY_PROTEIN, protein)
                .putInt(KEY_CARBS, carbs)
                .putInt(KEY_FAT, fat)
                .apply();
    }

    public int getKcal() { return sharedPreferences.getInt(KEY_KCAL, 0); }
    public void setKcal(int kcal) { sharedPreferences.edit().putInt(KEY_KCAL, kcal).apply(); }

    public int getProtein() { return sharedPreferences.getInt(KEY_PROTEIN, 0); }
    public void setProtein(int protein) { sharedPreferences.edit().putInt(KEY_PROTEIN, protein).apply(); }

    public int getCarbs() { return sharedPreferences.getInt(KEY_CARBS, 0); }
    public void setCarbs(int carbs) { sharedPreferences.edit().putInt(KEY_CARBS, carbs).apply(); }

    public int getFat() { return sharedPreferences.getInt(KEY_FAT, 0); }
    public void setFat(int fat) { sharedPreferences.edit().putInt(KEY_FAT, fat).apply(); }

    public void clearGeneratedData() {
        sharedPreferences.edit()
                .remove(KEY_KCAL)
                .remove(KEY_PROTEIN)
                .remove(KEY_CARBS)
                .remove(KEY_FAT)
                .apply();
    }
    public void clearAllData() {
        sharedPreferences.edit().clear().apply();
    }

    public UserInput getUser() {
        String userJson = sharedPreferences.getString(KEY_USER_INPUTS, null);
        return userJson != null ? new Gson().fromJson(userJson, UserInput.class) : new UserInput();
    }
    public void setUser(UserInput user) { saveUser(user); }

    public void saveUser(UserInput user) {
        sharedPreferences.edit()
                .putString(KEY_USER_INPUTS, new Gson().toJson(user))
                .apply();
    }

    public void clearUser() {
        sharedPreferences.edit().remove(KEY_USER_INPUTS).apply();
    }

    public List<Integer> getSelectedMealsID() {
        Set<String> mealIdSet = sharedPreferences.getStringSet(KEY_SELECTED_MEALS_ID, new HashSet<>());
        List<Integer> selectedMeals = new ArrayList<>();
        for (String id : mealIdSet) {
            selectedMeals.add(Integer.parseInt(id));
        }
        return selectedMeals;
    }
    public void setSelectedMealsID(List<Integer> selectedMeals) { saveSelectedMealsID(selectedMeals); }

    public void saveSelectedMealsID(List<Integer> selectedMeals) {
        Set<String> mealIdSet = new HashSet<>();
        for (Integer id : selectedMeals) {
            mealIdSet.add(String.valueOf(id));
        }
        sharedPreferences.edit().putStringSet(KEY_SELECTED_MEALS_ID, mealIdSet).apply();
    }

    public void clearSelectedMealIDs() {
        sharedPreferences.edit().remove(KEY_SELECTED_MEALS_ID).apply();
    }

    public List<Meal> getBreakfastMeals() { return getMealList(KEY_BREAKFAST_MEALS); }
    public void setBreakfastMeals(List<Meal> meals) { saveGeneratedMealPlan(meals, getLunchMeals(), getDinnerMeals(), getBaseCalories()); }

    public List<Meal> getLunchMeals() { return getMealList(KEY_LUNCH_MEALS); }
    public void setLunchMeals(List<Meal> meals) { saveGeneratedMealPlan(getBreakfastMeals(), meals, getDinnerMeals(), getBaseCalories()); }

    public List<Meal> getDinnerMeals() { return getMealList(KEY_DINNER_MEALS); }
    public void setDinnerMeals(List<Meal> meals) { saveGeneratedMealPlan(getBreakfastMeals(), getLunchMeals(), meals, getBaseCalories()); }

    public int getBaseCalories() { return sharedPreferences.getInt(KEY_BASE_CALORIES, 0); }
    public void setBaseCalories(int baseCalories) { saveGeneratedMealPlan(getBreakfastMeals(), getLunchMeals(), getDinnerMeals(), baseCalories); }

    public void saveGeneratedMealPlan(List<Meal> breakfastMeals, List<Meal> lunchMeals, List<Meal> dinnerMeals, int baseCalories) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(KEY_BREAKFAST_MEALS, gson.toJson(breakfastMeals));
        editor.putString(KEY_LUNCH_MEALS, gson.toJson(lunchMeals));
        editor.putString(KEY_DINNER_MEALS, gson.toJson(dinnerMeals));
        editor.putInt(KEY_BASE_CALORIES, baseCalories);
        editor.apply();
    }
    public void saveExercises(List<Exercise> exercises) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(KEY_EXERCISES, gson.toJson(exercises));
        editor.apply();
    }
    public List<Exercise> getExercises() {
        String json = sharedPreferences.getString(KEY_EXERCISES, null);
        if (json == null) {
            return new ArrayList<>(); // Return an empty list if no data is found
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Exercise>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private List<Meal> getMealList(String key) {
        String json = sharedPreferences.getString(key, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<Meal>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public boolean hasData() {
        return sharedPreferences.contains(KEY_KCAL) ||
                sharedPreferences.contains(KEY_PROTEIN) ||
                sharedPreferences.contains(KEY_CARBS) ||
                sharedPreferences.contains(KEY_FAT) ||
                sharedPreferences.contains(KEY_USER_INPUTS) ||
                sharedPreferences.contains(KEY_SELECTED_MEALS_ID) ||
                sharedPreferences.contains(KEY_BREAKFAST_MEALS) ||
                sharedPreferences.contains(KEY_LUNCH_MEALS) ||
                sharedPreferences.contains(KEY_DINNER_MEALS) ||
                sharedPreferences.contains(KEY_BASE_CALORIES);
    }
}
