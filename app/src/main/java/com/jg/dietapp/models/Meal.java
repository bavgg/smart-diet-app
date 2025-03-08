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
    private String culture;
    private String region;
    private int servingsGrams;
    private String mealtime;

    private boolean isSelected;

    public Meal(String name, double calories, int protein, int carbs, int fats, String dietType, String allergens, int prepTime, String culture, String region, int servingsGrams, String mealtime) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.dietType = dietType;
        this.allergens = allergens;
        this.prepTime = prepTime;
        this.culture = culture;
        this.region = region;
        this.servingsGrams = servingsGrams;
        this.mealtime = mealtime;
    }

    public String getName() { return name; }
    public double getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFats() { return fats; }
    public String getDietType() { return dietType; }
    public String getAllergens() { return allergens; }
    public int getPrepTime() { return prepTime; }
    public String getCulture() {
        return culture;
    }
    public String getRegion() {
        return region;
    }
    public int getServingsGrams() {
        return servingsGrams;
    }
    public String getMealtime() {
        return mealtime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

//    @Override
//    public String toString() {
//        return String.format(
//                "%s - %.2f kcal | %dg Protein | %dg Carbs | %dg Fats | Diet: %s | Allergens: %s | Prep Time: %d min | Culture: %s | Region: %s | Serving: %dg",
//                name, calories, protein, carbs, fats, dietType, allergens, prepTime, culture, region, servingsGrams
//        );
//    }

    @Override
    public String toString() {
        return String.format(
                "%s - %.2f kcal Serving: %dg ||",
                name, calories, servingsGrams
        );
    }

}


