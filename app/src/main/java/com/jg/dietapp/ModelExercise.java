package com.jg.dietapp;


public class ModelExercise {
    private String name;
    private String type;
    private int duration;  // in minutes
    private int caloriesBurned;

    public ModelExercise(String name, String type, int duration, int caloriesBurned) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getDuration() { return duration; }
    public int getCaloriesBurned() { return caloriesBurned; }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + duration + " mins | Burns " + caloriesBurned + " kcal";
    }
}
