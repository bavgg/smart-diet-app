package com.jg.dietapp.generator;

import com.jg.dietapp.utils.Utils;
import com.jg.dietapp.enums.EnumGoal;
import com.jg.dietapp.enums.EnumSex;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;

import java.util.*;
import java.util.stream.Collectors;

public class MealGenerator {
    private final UserInput userInput;
    private final List<Meal> mealOptions;
    private final double baseCalories;
    private List<Meal> breakfastMeals;
    private List<Meal> lunchMeals;
    private List<Meal> dinnerMeals;

    public MealGenerator(UserInput userInput, List<Meal> mealOptions) {
        this.userInput = userInput;
        this.mealOptions = mealOptions;

        double tdee = calculateTDEE();
        baseCalories = adjustCaloriesForGoal(tdee);
    }

    public void generateMeals() {
        double breakfastCalories = baseCalories * 0.3;
        double lunchCalories = baseCalories * 0.4;
        double dinnerCalories = baseCalories * 0.3;

        List<Meal> breakfastMealsR = getMealsByMealtime("Breakfast");
        List<Meal> lunchDinnerMeals = getMealsByMealtime("Lunch/Dinner");
        List<Meal> allMeals = getMealsByMealtime("All Meals");

        Collections.shuffle(breakfastMealsR);
        Collections.shuffle(lunchDinnerMeals);
        Collections.shuffle(allMeals);

        int mid = lunchDinnerMeals.size() / 2;
        List<Meal> lunchMealsR = new ArrayList<>(lunchDinnerMeals.subList(0, mid));
        List<Meal> dinnerMealsR = new ArrayList<>(lunchDinnerMeals.subList(mid, lunchDinnerMeals.size()));

        int size = allMeals.size();
        int partSize = size / 3;
        List<Meal> firstPartAllMeals = new ArrayList<>(allMeals.subList(0, partSize));
        List<Meal> secondPartAllMeals = new ArrayList<>(allMeals.subList(partSize, partSize * 2));
        List<Meal> thirdPartAllMeals = new ArrayList<>(allMeals.subList(partSize * 2, size));

        breakfastMealsR.addAll(firstPartAllMeals);
        lunchMealsR.addAll(secondPartAllMeals);
        dinnerMealsR.addAll(thirdPartAllMeals);

        breakfastMeals = selectRandomMeals("Breakfast", breakfastMealsR, breakfastCalories);
        lunchMeals = selectRandomMeals("Lunch", lunchMealsR, lunchCalories);
        dinnerMeals = selectRandomMeals("Dinner", dinnerMealsR, dinnerCalories);
    }

    public List<Meal> getBreakfastMeals() { return breakfastMeals; }
    public List<Meal> getLunchMeals() { return lunchMeals; }
    public List<Meal> getDinnerMeals() { return dinnerMeals; }
    public double getBaseCalories() { return baseCalories; }

    private List<Meal> getMealsByMealtime(String mealtime) {
        return mealOptions.stream()
                .filter(meal -> meal.getMealtime().equalsIgnoreCase(mealtime))
                .collect(Collectors.toList());
    }

    private double calculateTDEE() {
        double bmr = calculateBMR();
        switch (userInput.getActivityLevel()) {
            case SEDENTARY: return bmr * 1.2;
            case LIGHT_ACTIVITY: return bmr * 1.375;
            case MODERATE_ACTIVITY: return bmr * 1.55;
            case HEAVY_ACTIVITY: return bmr * 1.725;
            case EXCESSIVE_ACTIVITY: return bmr * 1.9;
            default: return bmr * 1.375;
        }
    }

    private double calculateBMR() {
        double weightKg = Utils.lbsToKg(userInput.getWeight());
        return (userInput.getSex() == EnumSex.MALE)
                ? 88.362 + (13.397 * weightKg) + (4.799 * userInput.getHeight()) - (5.677 * userInput.getAge())
                : 447.593 + (9.247 * weightKg) + (3.098 * userInput.getHeight()) - (4.330 * userInput.getAge());
    }

    private double adjustCaloriesForGoal(double tdee) {
        if (userInput.getGoal() == EnumGoal.LOSE_WEIGHT) return tdee - 300;
        if (userInput.getGoal() == EnumGoal.GAIN_MUSCLE) return tdee + 300;
        return tdee;
    }

    private List<Meal> selectRandomMeals(String mealtime, List<Meal> availableMeals, double targetCalories) {
        List<Meal> selectedMeals = new ArrayList<>();
        double totalCalories = 0;
        Collections.shuffle(availableMeals);

        if (!Objects.equals(mealtime, "Breakfast")) {
            Meal riceMeal = createRiceMeal();
            selectedMeals.add(riceMeal);
            totalCalories += riceMeal.getCalories();
        }

        for (Meal meal : availableMeals) {
            if (totalCalories + meal.getCalories() <= targetCalories) {
                selectedMeals.add(meal);
                totalCalories += meal.getCalories();
            }
            if (totalCalories >= targetCalories) break;
        }

        return selectedMeals;
    }

    private Meal createRiceMeal() {
        return new Meal(
                new Random().nextInt(1000) + 2000, "Rice", 200, 4, 45, 0, "Any", "None", 10, "Asian", "Global", 150, "Any"
        );
    }
}
