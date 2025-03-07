package com.jg.dietapp.models;


public class Meal {
    private String name;
    private double calories;
    private int protein;
    private int carbs;
    private int fats;
    private String dietType;
    private String allergens;
    private int prepTime; // minutes
    private String cuisine;
    private int servingsGrams;

    public Meal(String name, double calories, int protein, int carbs, int fats, String dietType, String allergens, int prepTime, String cuisine, int servingsGrams) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.dietType = dietType;
        this.allergens = allergens;
        this.prepTime = prepTime;
        this.cuisine = cuisine;
        this.servingsGrams = servingsGrams;
    }

    public String getName() { return name; }
    public double getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFats() { return fats; }
    public String getDietType() { return dietType; }
    public String getAllergens() { return allergens; }
    public int getPrepTime() { return prepTime; }
    public String getCuisine() {
        return cuisine;
    }
    public int getServingsGrams() {
        return servingsGrams;
    }

    @Override
    public String toString() {
        return name + " - " + calories + " kcal | " + protein + "g Protein | " + carbs + "g Carbs | " + fats + "g Fats | " + "Diet Type " + dietType +  " | Allergens " + allergens;
    }
}


