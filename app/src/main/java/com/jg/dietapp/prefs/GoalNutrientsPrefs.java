package com.jg.dietapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class GoalNutrientsPrefs {
    private static final String PREFS_NAME = "nutrition_prefs";
    private static final String KEY_KCAL = "kcal";
    private static final String KEY_PROTEIN = "protein";
    private static final String KEY_CARBS = "carbs";
    private static final String KEY_FAT = "fat";

    private final SharedPreferences sharedPreferences;

    public GoalNutrientsPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveNutritionData(int kcal, int protein, int carbs, int fat) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_KCAL, kcal);
        editor.putInt(KEY_PROTEIN, protein);
        editor.putInt(KEY_CARBS, carbs);
        editor.putInt(KEY_FAT, fat);
        editor.apply();
    }

    public int getKcal() {
        return sharedPreferences.getInt(KEY_KCAL, 0);
    }

    public int getProtein() {
        return sharedPreferences.getInt(KEY_PROTEIN, 0);
    }

    public int getCarbs() {
        return sharedPreferences.getInt(KEY_CARBS, 0);
    }

    public int getFat() {
        return sharedPreferences.getInt(KEY_FAT, 0);
    }

    public void clearSelectedMeals(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_KCAL).apply();
        prefs.edit().remove(KEY_PROTEIN).apply();
        prefs.edit().remove(KEY_CARBS).apply();
        prefs.edit().remove(KEY_FAT).apply();
    }
}
