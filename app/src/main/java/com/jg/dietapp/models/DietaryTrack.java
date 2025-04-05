package com.jg.dietapp.models;

import androidx.annotation.NonNull;

public class DietaryTrack {
    private int calories;
    private int protein;
    private int carbs;
    private int fat;

    public DietaryTrack(int calories, int protein, int carbs, int fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    // Getters
    public int getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFat() { return fat; }

    // Setters
    public void setCalories(int calories) { this.calories = calories; }
    public void setProtein(int protein) { this.protein = protein; }
    public void setCarbs(int carbs) { this.carbs = carbs; }
    public void setFat(int fat) { this.fat = fat; }

    @NonNull
    @Override
    public String toString() {
        return "NutritionData{" +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", fat=" + fat +
                '}';
    }
}
