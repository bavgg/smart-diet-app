package com.jg.dietapp.generator;


import android.content.Context;

import com.jg.dietapp.utils.Utils;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.enums.EnumGoal;
import com.jg.dietapp.enums.EnumSex;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.shared.UserInput;

import java.util.ArrayList;
import java.util.List;

public class GeneratorMeal {
    UserInput userInput;
    double baseCalories;

    public GeneratorMeal(UserInput userInput) {
        this.userInput = userInput;
    }

    List<Meal> mealOptions;
    public List<Meal>[] generateMealPlan(Context context) {
        DAOMeal mealDAO = new DAOMeal(context);
        List<Meal> mealPlan = new ArrayList<>();

        List<Meal> breakfastMealPlan = new ArrayList<>();
        List<Meal> lunchMealPlan = new ArrayList<>();
        List<Meal> dinnerMealPlan = new ArrayList<>();

//      // lactose-free, gluten-free, nut-free

        // Get filtered meal options based on diet type and allergens
        mealOptions = mealDAO.getMealsByDietAndAllergens(
                userInput.getDietType().toString(),
                userInput.getFoodAllergens()
        );

        System.out.println("Meal Options: " + mealOptions);


        // Step 1: Calculate Daily Calorie Needs
        double TDEE = calculateTDEE();


        baseCalories = TDEE;
        // Step 2: Adjust Calories Based on Goal
        if (userInput.getGoal() == EnumGoal.LOSE_WEIGHT) {
            baseCalories -= 300;
        } else if (userInput.getGoal() == EnumGoal.GAIN_MUSCLE) {
            baseCalories += 300;
        }

        System.out.println("Base Calories after: " + baseCalories);

        // Step 3: Distribute Calories Across Meals
        double breakfastCalories = baseCalories * 0.3; // 30% for Breakfast
        double lunchCalories = baseCalories * 0.4; // 40% for Lunch
        double dinnerCalories = baseCalories * 0.3; // 30% for Dinner

        // Step 4: Fetch Meals Matching Diet, Allergens, and Calories
        breakfastMealPlan.addAll(selectMeals(breakfastCalories));
        lunchMealPlan.addAll(selectMeals(lunchCalories));
        dinnerMealPlan.addAll(selectMeals(dinnerCalories));

        return new List[]{breakfastMealPlan, lunchMealPlan, dinnerMealPlan};
    }

    public double getBaseCalories() {
        return baseCalories;
    }

    private double calculateTDEE() {
        double bmr = calculateBMR(userInput.getWeight(), userInput.getHeight(), userInput.getAge(), userInput.getSex());
        System.out.println("BMR " +bmr);

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

    private List<Meal> selectMeals(double targetCalories) {
        List<Meal> selectedMeals = new ArrayList<>();
        double totalCalories = 0;

        while (!mealOptions.isEmpty() && totalCalories < targetCalories) {
            Meal bestMatch = null;
            double minDifference = Double.MAX_VALUE;

            for (Meal meal : mealOptions) {
                double diff = Math.abs((totalCalories + meal.getCalories()) - targetCalories);
                if (diff < minDifference) {
                    minDifference = diff;
                    bestMatch = meal;
                }
            }

            if (bestMatch == null) break;

            selectedMeals.add(bestMatch);
            totalCalories += bestMatch.getCalories();
            mealOptions.remove(bestMatch);
        }

        return selectedMeals;
    }


}
