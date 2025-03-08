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
import java.util.stream.Collectors;

public class GeneratorMeal {
    UserInput userInput;
    double baseCalories;

    public GeneratorMeal(UserInput userInput) {
        this.userInput = userInput;
    }

    List<Meal> mealOptions;
    public List<Meal>[] generateMealPlan(Context context) {
        DAOMeal mealDAO = new DAOMeal(context);

        List<Meal> breakfastMealPlan = new ArrayList<>();
        List<Meal> lunchMealPlan = new ArrayList<>();
        List<Meal> dinnerMealPlan = new ArrayList<>();

        // Get filtered meal options based on diet type and allergens
        mealOptions = mealDAO.getMealsByDietAndAllergens(
                userInput.getDietType().toString(),
                userInput.getFoodAllergens()
        );

        System.out.println("Meal Options: " + mealOptions);

        // Categorize meals by mealtime
        List<Meal> breakfastOptions = mealOptions.stream()
                .filter(meal -> meal.getMealtime().equalsIgnoreCase("Breakfast"))
                .collect(Collectors.toList());

        List<Meal> lunchDinnerOptions = mealOptions.stream()
                .filter(meal -> meal.getMealtime().equalsIgnoreCase("Lunch/Dinner"))
                .collect(Collectors.toList());

//        List<Meal> dinnerOptions = new ArrayList<>(lunchOptions); // Dinner can share options with lunch

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

        // Step 4: Fetch Meals Matching Diet, Allergens, Mealtime, and Calories
        breakfastMealPlan.addAll(selectMeals(breakfastOptions, breakfastCalories));
        lunchMealPlan.addAll(selectMeals(lunchDinnerOptions, lunchCalories));
        dinnerMealPlan.addAll(selectMeals(lunchDinnerOptions, dinnerCalories));

        System.out.println("Breakfast Plan: " + breakfastMealPlan);
        System.out.println("Lunch Plan: " + lunchMealPlan);
        System.out.println("Dinner Plan: " + dinnerMealPlan);

        return new List[]{breakfastMealPlan, lunchMealPlan, dinnerMealPlan};
    }

    public double getBaseCalories() {
        return baseCalories;
    }

    private double calculateTDEE() {
        double bmr = calculateBMR(userInput.getWeight(), userInput.getHeight(), userInput.getAge(), userInput.getSex());
        System.out.println("BMR " + bmr);

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

    private List<Meal> selectMeals(List<Meal> availableMeals, double targetCalories) {
        List<Meal> selectedMeals = new ArrayList<>();
        double totalCalories = 0;

        // Always include Rice
        Meal riceMeal = new Meal(
                "Rice",
                200,  // Calories
                4,    // Protein
                45,   // Carbs
                0,    // Fats
                "Any",
                "None",
                10,   // Prep Time
                "Asian",
                "Global",
                150,  // Servings in grams
                "Any"
        );
        selectedMeals.add(riceMeal);
        totalCalories += riceMeal.getCalories();

        // Sort meals by calorie content (descending) for better fit
        availableMeals.sort((a, b) -> Double.compare(b.getCalories(), a.getCalories()));

        // Pick meals to get as close as possible to targetCalories
        for (Meal meal : availableMeals) {
            if (totalCalories + meal.getCalories() <= targetCalories) {
                selectedMeals.add(meal);
                totalCalories += meal.getCalories();
            }

            // If we reach or exceed targetCalories, stop adding more meals
            if (totalCalories >= targetCalories) break;
        }

        // If the last meal overshot the calories, try to remove and replace with a better fit
        if (totalCalories > targetCalories) {
            for (int i = selectedMeals.size() - 1; i > 0; i--) {  // Skip Rice (index 0)
                Meal lastMeal = selectedMeals.get(i);
                if ((totalCalories - lastMeal.getCalories()) <= targetCalories) {
                    totalCalories -= lastMeal.getCalories();
                    selectedMeals.remove(i);
                    break;
                }
            }
        }

        return selectedMeals;
    }

}
