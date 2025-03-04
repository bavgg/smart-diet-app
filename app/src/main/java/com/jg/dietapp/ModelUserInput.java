package com.jg.dietapp;

import androidx.annotation.NonNull;

import java.util.List;

public class ModelUserInput {
    private EnumGoal goal;
    private EnumSex sex;
    private int age;
    private int height;
    private double weight;
    private double goalWeight;
    private EnumActivityLevel activityLevel;
    private EnumDietType dietType;
//    private EnumFoodAllergen[] foodAllergens;
    private boolean userSubmitted = false;
    private String foodAllergens;

    public ModelUserInput(){

    }
    public ModelUserInput(EnumGoal goal, EnumSex sex, int age, int height, int weight, double goalWeight,
                          EnumActivityLevel activityLevel, EnumDietType dietType, String foodAllergens) {
        this.goal = goal;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.activityLevel = activityLevel;
        this.dietType = dietType;
        this.foodAllergens = foodAllergens;
    }

    // Getters
    public boolean getUserSubmitted() { return userSubmitted; }
    public EnumGoal getGoal() { return goal; }
    public EnumSex getSex() { return sex; }
    public int getAge() { return age; }
    public int getHeight() { return height; }
    public double getWeight() { return weight; }
    public double getGoalWeight() { return goalWeight; }
    public EnumActivityLevel getActivityLevel() { return activityLevel; }
    public EnumDietType getDietType() { return dietType; }
    public String getFoodAllergens() { return foodAllergens; }

    // Setters
    public void setUserSubmitted(boolean userSubmitted) { this.userSubmitted = userSubmitted; }
    public void setGoal(EnumGoal goal) { this.goal = goal; }
    public void setSex(EnumSex sex) { this.sex = sex; }
    public void setAge(int age) { this.age = age; }
    public void setHeight(int height) { this.height = height; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setGoalWeight(double goalWeight) { this.goalWeight = goalWeight; }
    public void setActivityLevel(EnumActivityLevel activityLevel) { this.activityLevel = activityLevel; }
    public void setDietType(EnumDietType dietType) { this.dietType = dietType; }
    public void setFoodAllergens(String foodAllergens) { this.foodAllergens = foodAllergens; }


    @NonNull
    @Override
    public String toString() {
        return "ModelUserInput{" +
                "goal=" + goal +
                ", sex=" + sex +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", goalWeight=" + goalWeight +
                ", activityLevel=" + activityLevel +
                ", dietType=" + dietType +
                ", foodAllergens=" + foodAllergens +
                '}';
    }
}
