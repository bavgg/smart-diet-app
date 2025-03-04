package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.userInput;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);


//        GeneratorMeal mealGenerator = new GeneratorMeal();

//        userInput = getUserInput(); // Assume user input is retrieved
//        List<ModelMeal> meals = mealGenerator.generateMealPlan(this, userInput);
//        List<ModelExercise> exercises = ExerciseGenerator.generateExercisePlan(this, userInput);

//        // Generate Exercise Plan
//        List<ModelExercise> exercisePlan = GeneratorExercise.generateExercisePlan();
//        System.out.println("\n===== Exercise Plan =====");
//        for (ModelExercise exercise : exercisePlan) {
//            System.out.println(exercise);
//        }
    }
}
