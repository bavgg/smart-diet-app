package com.jg.dietapp.models;

public class GeneratedExercise {
    private int id;
    private int userId;
    private String exerciseName;
    private int duration; // in minutes
    private int caloriesBurned;
    private String createdAt;

    // Constructor
    public GeneratedExercise(int id, int userId, String exerciseName, int duration, int caloriesBurned, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.exerciseName = exerciseName;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getDuration() {
        return duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // toString() for debugging/logging
    @Override
    public String toString() {
        return "GeneratedExercise{" +
                "id=" + id +
                ", userId=" + userId +
                ", exerciseName='" + exerciseName + '\'' +
                ", duration=" + duration +
                ", caloriesBurned=" + caloriesBurned +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}

