package com.jg.dietapp.models;

import androidx.annotation.NonNull;

import com.jg.dietapp.enums.EnumActivityLevel;
import com.jg.dietapp.enums.EnumDietType;
import com.jg.dietapp.enums.EnumGoal;
import com.jg.dietapp.enums.EnumSex;

public class UserInput {
    private EnumGoal goal;
    private EnumSex sex;
    private int age;
    private int height;
    private double weight;
    private EnumActivityLevel activityLevel;
    private EnumDietType dietType;
    private boolean userSubmitted = false;
    private String foodAllergens;

    public UserInput(){

    }
    public UserInput(EnumGoal goal, EnumSex sex, int age, int height, int weight,
                     EnumActivityLevel activityLevel, EnumDietType dietType, String foodAllergens, boolean userSubmitted) {
        this.goal = goal;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.dietType = dietType;
        this.foodAllergens = foodAllergens;
        this.userSubmitted = userSubmitted;
    }
    public UserInput(EnumGoal goal, EnumSex sex, int age, int height, int weight, double goalWeight,
                     EnumActivityLevel activityLevel, EnumDietType dietType, String foodAllergens) {
        this.goal = goal;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
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
    public void setActivityLevel(EnumActivityLevel activityLevel) { this.activityLevel = activityLevel; }
    public void setDietType(EnumDietType dietType) { this.dietType = dietType; }
    public void setFoodAllergens(String foodAllergens) { this.foodAllergens = foodAllergens; }


    @NonNull
    @Override
    public String toString() {
        return "ModelUserInput{" +
                "user=" + userSubmitted +
                "goal=" + goal +
                ", sex=" + sex +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", activityLevel=" + activityLevel +
                ", dietType=" + dietType +
                ", foodAllergens=" + foodAllergens +
                '}';
    }
}
