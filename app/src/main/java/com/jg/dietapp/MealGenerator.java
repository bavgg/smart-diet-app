package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.userInput;

import java.util.ArrayList;
import java.util.List;

public class MealGenerator {
    public List<ModelMeal> generateMealPlan() {
        List<ModelMeal> meals = new ArrayList<>();

        int baseCalories = getBaseCalories(userInput.getActivityLevel());


        if (userInput.getGoal() == EnumGoal.LOSE_WEIGHT) {
            baseCalories -= 300;
        } else if (userInput.getGoal() == EnumGoal.GAIN_MUSCLE) {
            baseCalories += 300;
        }

        meals.add(selectMeal("Breakfast", baseCalories / 3));
        meals.add(selectMeal("Lunch", baseCalories / 3));
        meals.add(selectMeal("Dinner", baseCalories / 3));

        return meals;
    }

    private int getBaseCalories(EnumActivityLevel activityLevel) {
        switch (activityLevel) {
            case SEDENTARY: return 1800;
            case LIGHT_ACTIVITY: return 2000;
            case MODERATE_ACTIVITY: return 2200;
            case HEAVY_ACTIVITY: return 2500;
            case EXCESSIVE_ACTIVITY: return 2800;
            default: return 2000;
        }
    }

    private ModelMeal selectMeal(String mealType, int calories) {
        List<ModelMeal> mealOptions = new ArrayList<>();

        switch (userInput.getDietType()) {
            case KETO:
                mealOptions.add(new ModelMeal("Avocado Omelet", calories, 20, 5, 30));
                mealOptions.add(new ModelMeal("Grilled Chicken Salad", calories, 40, 10, 25));
                break;
            case VEGETARIAN:
                mealOptions.add(new ModelMeal("Vegetable Stir Fry", calories, 10, 30, 10));
                mealOptions.add(new ModelMeal("Quinoa Salad", calories, 15, 35, 5));
                break;
            default: // General diet
                mealOptions.add(new ModelMeal("Chicken & Rice", calories, 40, 50, 10));
                mealOptions.add(new ModelMeal("Salmon & Broccoli", calories, 45, 20, 30));
                break;
        }

        for (ModelMeal meal : mealOptions) {
            if (!containsAllergen(meal)) {
                return meal;
            }
        }

        return new ModelMeal("Custom Meal", calories, 30, 20, 15);
    }

    private boolean containsAllergen(ModelMeal meal) {
        for (EnumFoodAllergen allergen : userInput.getFoodAllergens()) {
            if (meal.getName().toLowerCase().contains(allergen.name().toLowerCase())) {
                return true;
            }
        }
        return false;
    }


}
