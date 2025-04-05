package com.jg.dietapp.models;


public class Exercise {
    private String name;
    private String activityLevel;
    private int duration;  // in minutes
    private int caloriesBurned;
    private String imageName;

    public Exercise(String name, int duration, int caloriesBurned) {
        this.name = name;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.imageName = name + ".jpg";
    }
    public Exercise(String name, String activityLevel, int duration, int caloriesBurned) {
        this.name = name;
        this.activityLevel = activityLevel;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.imageName = name + ".jpg";
    }

    public String getName() { return name; }
    public String getActivityLevel() { return activityLevel; }
    public int getDuration() { return duration; }
    public int getCaloriesBurned() { return caloriesBurned; }
    public String getImageName() { return imageName; }

    @Override
    public String toString() {
        return name + " (" + activityLevel + ") - " + duration + " mins | Burns " + caloriesBurned + " kcal";
    }
}
