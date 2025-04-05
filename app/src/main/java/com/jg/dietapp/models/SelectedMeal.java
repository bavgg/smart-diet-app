package com.jg.dietapp.models;

public class SelectedMeal {
    private int userId; // User ID who selected the meal
    private int mealId; // The ID of the selected meal

    // Constructor
    public SelectedMeal( int userId, int mealId) {
        this.userId = userId;
        this.mealId = mealId;
    }

    // Getters and Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }


}