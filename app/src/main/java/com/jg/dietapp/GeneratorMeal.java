package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.userInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeneratorMeal {
    public List<ModelMeal> generateMealPlan() {
        List<ModelMeal> meals = new ArrayList<>();

        double baseCalories = getBaseCalories(userInput.getActivityLevel());


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


    private double getBaseCalories(EnumActivityLevel activityLevel) {
        switch (activityLevel) {
            case SEDENTARY: return calculateBMR(userInput.getWeight(), userInput.getHeight(), userInput.getAge(), userInput.getSex());
            case LIGHT_ACTIVITY: return 2000;
            case MODERATE_ACTIVITY: return 2200;
            case HEAVY_ACTIVITY: return 2500;
            case EXCESSIVE_ACTIVITY: return 2800;
            default: return 2000;
        }
    }

    private ModelMeal selectMeal(String mealType, double calories) {
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

    private double calculateBMR(double weight, double height, int age, EnumSex gender) {
        if(userInput.getSex() == EnumSex.MALE) {
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

//    private double calculateTDEE(double bmr, EnumActivityLevel activityLevel) {
//        switch (activityLevel) {
//            case SEDENTARY: return bmr * 1.2;
//            case LIGHT_ACTIVITY: return bmr * 1.375;
//
//        }
//    }


}
