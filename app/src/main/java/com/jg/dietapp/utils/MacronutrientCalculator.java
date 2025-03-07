package com.jg.dietapp.utils;

public class MacronutrientCalculator {
    double totalCalories;
    double proteinGrams, carbGrams, fatGrams;

    public MacronutrientCalculator(double totalCalories) {
        this.totalCalories = totalCalories;

        this.proteinGrams = (totalCalories * 0.25) / 4;  // 25% Protein
        this.carbGrams = (totalCalories * 0.50) / 4;      // 50% Carbs
        this.fatGrams = (totalCalories * 0.25) / 9;       // 25% Fat
    }
    public double getProtein() { return proteinGrams; }
    public double getCarbs() { return carbGrams; }
    public double getFat() { return fatGrams; }

}
