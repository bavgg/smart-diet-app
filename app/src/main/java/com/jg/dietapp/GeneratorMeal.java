package com.jg.dietapp;


import static com.jg.dietapp.MainActivity.userInput;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneratorMeal {
    ModelUserInput userInput;

    public GeneratorMeal(ModelUserInput userInput) {
        this.userInput = userInput;
    }

    List<ModelMeal> mealOptions;
    public List<ModelMeal> generateMealPlan(Context context) {
        DAOMeal mealDAO = new DAOMeal(context);
        List<ModelMeal> mealPlan = new ArrayList<>();

//      // lactose-free, gluten-free, nut-free

        // Get filtered meal options based on diet type and allergens
        mealOptions = mealDAO.getMealsByDietAndAllergens(
                userInput.getDietType().toString(),
                userInput.getFoodAllergens()
        );

//        System.out.println("Meal Options: " + mealOptions);


        // Step 1: Calculate Daily Calorie Needs
        double baseCalories = getBaseCalories();

        // Step 2: Adjust Calories Based on Goal
        if (userInput.getGoal() == EnumGoal.LOSE_WEIGHT) {
            baseCalories -= 300;
        } else if (userInput.getGoal() == EnumGoal.GAIN_MUSCLE) {
            baseCalories += 300;
        }

        // Step 3: Distribute Calories Across Meals
        double breakfastCalories = baseCalories * 0.3; // 30% for Breakfast
        double lunchCalories = baseCalories * 0.4; // 40% for Lunch
        double dinnerCalories = baseCalories * 0.3; // 30% for Dinner

        // Step 4: Fetch Meals Matching Diet, Allergens, and Calories
        mealPlan.add(selectMeal( breakfastCalories));
        mealPlan.add(selectMeal(  lunchCalories));
        mealPlan.add(selectMeal(  dinnerCalories));

        return mealPlan;
    }

    public double getBaseCalories() {
        double bmr = calculateBMR(userInput.getWeight(), userInput.getHeight(), userInput.getAge(), userInput.getSex());

        switch (userInput.getActivityLevel()) {
            case SEDENTARY: return bmr * 1.2;
            case LIGHT_ACTIVITY: return bmr * 1.375;
            case MODERATE_ACTIVITY: return bmr * 1.55;
            case HEAVY_ACTIVITY: return bmr * 1.725;
            case EXCESSIVE_ACTIVITY: return bmr * 1.9;
            default: return bmr * 1.375;
        }
    }

    private double calculateBMR(double weight, double height, int age, EnumSex sex) {
        double weightKg = Utils.lbsToKg(weight);
        if (sex == EnumSex.MALE) {
            return 88.362 + (13.397 * weightKg) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weightKg) + (3.098 * height) - (4.330 * age);
        }
    }

    private ModelMeal selectMeal(double targetCalories) {
        // Select a meal closest to the calorie target
        ModelMeal bestMatch = null;
        double minDifference = Double.MAX_VALUE;

        for (ModelMeal meal : mealOptions) {
            double diff = Math.abs(meal.getCalories() - targetCalories);
            if (diff < minDifference) {
                minDifference = diff;
                bestMatch = meal;
            }
        }

        mealOptions.remove(bestMatch);

        return bestMatch;
    }


}
