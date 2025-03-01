package com.jg.dietapp;

import java.util.ArrayList;
import java.util.List;

public class SharedDataUser {
    private String goal;
    private String sex;
    private String age;
    private String height;
    private String weight;
    private String activityLevel;
    private String currentWeight;
    private String goalWeight;
    private String dietPreference;
    private List<String> foodRestrictions;

    private List<String> all = new ArrayList<>();

    public List<String> getAll() {
        all.add(goal);
        all.add(sex);
        all.add(age);
        all.add(height);
        all.add(weight);
        all.add(activityLevel);
        all.add(currentWeight);
        all.add(goalWeight);

        return all;
    }

    public String getGoal() {
        return goal;
    }
    public String getSex() {
        return sex;
    }
    public String getAge() {
        return age;
    }
    public String getHeight() {
        return height;
    }
    public String getWeight() {
        return weight;
    }
    public String getActivityLevel() {
        return activityLevel;
    }
    public String getCurrentWeight() {
        return currentWeight;
    }
    public String getGoalWeight() {
        return goalWeight;
    }
    public String getDietPreference() {
        return dietPreference;
    }
    public List<String> getFoodRestrictions() {
        return foodRestrictions;
    }



    public void setGoal(String goal) {
        if (goal.equals("fat loss") || goal.equals("muscle gain") || goal.equals("weight maintenance")) {
            this.goal = goal;
        }
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public void setActivityLevel(String activityLevel) {
        if (goal.equals("sedentary") || goal.equals("lightly active") || goal.equals("moderately active") || goal.equals("very active") || goal.equals("professional athlete")) {
            this.activityLevel = activityLevel;
        }
    }
    public void setCurrentWeight(String currentWeight) {
        this.currentWeight = currentWeight;
    }
    public void setGoalWeight(String goalWeight) {
        this.goalWeight = goalWeight;
    }
    public void setDietPreference(String dietPreference) {
        this.dietPreference = dietPreference;
    }
    public void setFoodRestrictions(List<String> foodRestrictions) {
        this.foodRestrictions = foodRestrictions;
    }


}
