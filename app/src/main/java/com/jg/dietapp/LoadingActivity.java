package com.jg.dietapp;

import static com.jg.dietapp.utils.Utils.loadImagesFromAssetToInternalStorage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.jg.dietapp.data.DAOExercise;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.data.DAOPrefs;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.generator.MealGenerator;
import com.jg.dietapp.models.DietaryTrack;
import com.jg.dietapp.models.Exercise;

import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.AuthPrefs;
import com.jg.dietapp.prefs.ConfigurationPrefs;
import com.jg.dietapp.prefs.LoadPrefs;

import java.util.List;

public class LoadingActivity extends AppCompatActivity {
    LoadPrefs loadPrefs;

    ConfigurationPrefs configurationPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);


        // NOTE: Load data from database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        DAOPrefs daoPrefs = new DAOPrefs(dbHelper);
        AuthPrefs authPrefs = new AuthPrefs(this);
        int userID = authPrefs.getUserId();

        // Initialize objects
        loadPrefs = new LoadPrefs(this);
        configurationPrefs = new ConfigurationPrefs(this);
        DAOMeal mealDAO = new DAOMeal(dbHelper);
        DAOExercise exerciseDAO = new DAOExercise(dbHelper);

        // NOTE: Load images from assets to internal storage
        if(!configurationPrefs.isAssetLoaded()) {
            loadImagesFromAssetToInternalStorage(this);
            configurationPrefs.setAssetLoaded(true);
        }


        // NOTE: If user already exist
        if(!daoPrefs.getGeneratedMealsByUserId(userID, "breakfast").isEmpty()) {
            // Get database data
            UserInput userInput = daoPrefs.getUserInputByUserId(userID);
            DietaryTrack currentDietaryTrack = daoPrefs.getCurrentDietaryTrackByUserId(userID);
            List<Integer> selectedMealIDs = daoPrefs.getSelectedMealIDsByUserIDs(userID);
            List<Meal> generatedBreakfastMeals = daoPrefs.getGeneratedMealsByUserId(userID, "breakfast");
            List<Meal> generatedLunchMeals = daoPrefs.getGeneratedMealsByUserId(userID, "lunch");
            List<Meal> generatedDinnerMeals = daoPrefs.getGeneratedMealsByUserId(userID, "dinner");
            List<Exercise> generatedExercises = daoPrefs.getGeneratedExercisesByUserId(userID);
            int baseCalories = daoPrefs.getBaseCaloriesByUserID(userID);

            // Save data to prefs
            loadPrefs.saveUserInput(userInput);
            loadPrefs.saveCurrentDietaryTrack(currentDietaryTrack);
            loadPrefs.saveSelectedMealsID(selectedMealIDs);
            loadPrefs.saveGeneratedMealPlan(generatedBreakfastMeals, generatedLunchMeals, generatedDinnerMeals);
            loadPrefs.setBaseCalories(baseCalories);
            loadPrefs.saveGeneratedExercises(generatedExercises);
        }else {

            // NOTE: If user is new
            UserInput user = loadPrefs.getUserInput();
            Log.d("LoadingActivity", "User: " + user);
            if (user != null && user.getUserSubmitted()) {

                // If shared prefs is not yet loaded
                if(loadPrefs.getGeneratedBreakfastMeals().isEmpty()) {
                    UserInput userInputs = loadPrefs.getUserInput();
                    Log.d("LoadingActivity", "User Inputs: " + userInputs);
                    // Generate exercises
                    List<Exercise> filteredExercises = exerciseDAO.getExercisesByActivityLevel(userInputs.getActivityLevel().toString());
                    loadPrefs.saveGeneratedExercises(filteredExercises);
                    // Generate meals
                    List<Meal> filteredMeals = mealDAO.getMealsByDietAndAllergens(userInputs);
                    MealGenerator mealGenerator = new MealGenerator(userInputs, filteredMeals);
                    mealGenerator.generateMeals();
                    loadPrefs.saveGeneratedMealPlan(mealGenerator.getBreakfastMeals(), mealGenerator.getLunchMeals(), mealGenerator.getDinnerMeals());
                    loadPrefs.setBaseCalories((int) mealGenerator.getBaseCalories());
                }

                // Proceed to another activity
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(LoadingActivity.this, UserInputActivity.class));
            }
            finish();
        }

    }
}
