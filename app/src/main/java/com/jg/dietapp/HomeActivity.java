package com.jg.dietapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);


        MealGenerator mealGenerator = new MealGenerator();

        // Generate Meal Plan
        List<ModelMeal> mealPlan = mealGenerator.generateMealPlan();
        System.out.println("===== Meal Plan =====");
        for (ModelMeal meal : mealPlan) {
            System.out.println(meal);
        }

        // Generate Exercise Plan
        List<ModelExercise> exercisePlan = GeneratorExercise.generateExercisePlan();
        System.out.println("\n===== Exercise Plan =====");
        for (ModelExercise exercise : exercisePlan) {
            System.out.println(exercise);
        }
    }
}
