package com.jg.dietapp;

import java.util.List;


public class ModelMeal {
    private String name;
    private double calories;
    private int protein;
    private int carbs;
    private int fats;

    public ModelMeal(String name, double calories, int protein, int carbs, int fats) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getName() { return name; }
    public double getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFats() { return fats; }

    @Override
    public String toString() {
        return name + " - " + calories + " kcal | " + protein + "g Protein | " + carbs + "g Carbs | " + fats + "g Fats";
    }
}


