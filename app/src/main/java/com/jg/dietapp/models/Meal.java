package com.jg.dietapp.models;


public class Meal {
    private int id;
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
    private String imageName;

//    private boolean isSelected;

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
        this.imageName = this.name + ".jpg";
    }

    public Meal(String name, String culture, String region, String dietType, String mealtime, String allergens, double calories, int carbs, int fats, int protein, int prepTime, int servingsGrams) {
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
        this.imageName = this.name + ".jpg";
    }
    public Meal(int id, String name, double calories, int protein, int carbs, int fats, String dietType, String allergens, int prepTime, String culture, String region, int servingsGrams, String mealtime) {
        this.id = id;
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
        this.imageName = this.name + ".jpg";
    }

    public Meal(int id, String name, double calories, int protein, int carbs, int fats, String dietType, String allergens, int prepTime, String culture, String region, int servingsGrams, String mealtime, String imageName) {
        this.id = id;
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
        this.imageName = imageName;
    }

    public int getId() {return id; }
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
    public String getImageName() {
        return imageName;
    }

//    public boolean isSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }

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


