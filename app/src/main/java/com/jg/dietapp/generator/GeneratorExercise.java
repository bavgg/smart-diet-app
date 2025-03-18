package com.jg.dietapp.generator;




import static com.jg.dietapp.UserInputActivity.userInput;

import com.jg.dietapp.enums.EnumActivityLevel;
import com.jg.dietapp.enums.EnumGoal;
import com.jg.dietapp.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class GeneratorExercise {
    public static List<Exercise> generateExercisePlan() {
        List<Exercise> exercises = new ArrayList<>();

        if (userInput.getGoal()== EnumGoal.LOSE_WEIGHT) {
            if (userInput.getActivityLevel() == EnumActivityLevel.SEDENTARY) {
                exercises.add(new Exercise("Walking", "Cardio", 30, 150));
            } else {
                exercises.add(new Exercise("Running", "Cardio", 45, 400));
                exercises.add(new Exercise("Strength Training", "Strength", 30, 250));
            }
        } else if (userInput.getGoal() == EnumGoal.GAIN_MUSCLE) {
            exercises.add(new Exercise("Weight Lifting", "Strength", 45, 300));
            exercises.add(new Exercise("Pull-ups", "Strength", 20, 180));
        } else {
            exercises.add(new Exercise("Yoga", "Flexibility", 30, 100));
            exercises.add(new Exercise("Cycling", "Cardio", 40, 250));
        }

        return exercises;
    }
}

