package com.jg.dietapp.models;

import androidx.annotation.NonNull;

public class GeneratedMeal {
    private int id;
    private int userId;
    private String name;
    private String culture;
    private String region;
    private String dietType;
    private String mealtime;
    private String allergens;
    private String imageName;
    private double calories;
    private int carbs;
    private int fats;
    private int protein;
    private int prepTime;
    private int servingsGrams;

    public GeneratedMeal() {
    }

    public GeneratedMeal(int id, int userId, String name, String culture, String region, String dietType,
                      String mealtime, String allergens, String imageName, double calories,
                      int carbs, int fats, int protein, int prepTime, int servingsGrams) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.culture = culture;
        this.region = region;
        this.dietType = dietType;
        this.mealtime = mealtime;
        this.allergens = allergens;
        this.imageName = imageName;
        this.calories = calories;
        this.carbs = carbs;
        this.fats = fats;
        this.protein = protein;
        this.prepTime = prepTime;
        this.servingsGrams = servingsGrams;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getCulture() { return culture; }
    public String getRegion() { return region; }
    public String getDietType() { return dietType; }
    public String getMealtime() { return mealtime; }
    public String getAllergens() { return allergens; }
    public String getImageName() { return imageName; }
    public double getCalories() { return calories; }
    public int getCarbs() { return carbs; }
    public int getFats() { return fats; }
    public int getProtein() { return protein; }
    public int getPrepTime() { return prepTime; }
    public int getServingsGrams() { return servingsGrams; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setCulture(String culture) { this.culture = culture; }
    public void setRegion(String region) { this.region = region; }
    public void setDietType(String dietType) { this.dietType = dietType; }
    public void setMealtime(String mealtime) { this.mealtime = mealtime; }
    public void setAllergens(String allergens) { this.allergens = allergens; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public void setCalories(double calories) { this.calories = calories; }
    public void setCarbs(int carbs) { this.carbs = carbs; }
    public void setFats(int fats) { this.fats = fats; }
    public void setProtein(int protein) { this.protein = protein; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }
    public void setServingsGrams(int servingsGrams) { this.servingsGrams = servingsGrams; }

    @NonNull
    @Override
    public String toString() {
        return "BreakfastMeal{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", culture='" + culture + '\'' +
                ", region='" + region + '\'' +
                ", dietType='" + dietType + '\'' +
                ", mealtime='" + mealtime + '\'' +
                ", allergens='" + allergens + '\'' +
                ", imageName='" + imageName + '\'' +
                ", calories=" + calories +
                ", carbs=" + carbs +
                ", fats=" + fats +
                ", protein=" + protein +
                ", prepTime=" + prepTime +
                ", servingsGrams=" + servingsGrams +
                '}';
    }
}
