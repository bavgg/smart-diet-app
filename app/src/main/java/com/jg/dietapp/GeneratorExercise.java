package com.jg.dietapp;




import static com.jg.dietapp.MainActivity.userInput;

import java.util.ArrayList;
import java.util.List;

public class GeneratorExercise {
    public static List<ModelExercise> generateExercisePlan() {
        List<ModelExercise> exercises = new ArrayList<>();

        if (userInput.getGoal()== EnumGoal.LOSE_WEIGHT) {
            if (userInput.getActivityLevel() == EnumActivityLevel.SEDENTARY) {
                exercises.add(new ModelExercise("Walking", "Cardio", 30, 150));
            } else {
                exercises.add(new ModelExercise("Running", "Cardio", 45, 400));
                exercises.add(new ModelExercise("Strength Training", "Strength", 30, 250));
            }
        } else if (userInput.getGoal() == EnumGoal.GAIN_MUSCLE) {
            exercises.add(new ModelExercise("Weight Lifting", "Strength", 45, 300));
            exercises.add(new ModelExercise("Pull-ups", "Strength", 20, 180));
        } else {
            exercises.add(new ModelExercise("Yoga", "Flexibility", 30, 100));
            exercises.add(new ModelExercise("Cycling", "Cardio", 40, 250));
        }

        return exercises;
    }
}

