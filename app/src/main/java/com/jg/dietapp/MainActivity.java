package com.jg.dietapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jg.dietapp.data.DAOPrefs;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.fragments.main.FragmentMeals;
import com.jg.dietapp.fragments.main.FragmentPlan;
import com.jg.dietapp.fragments.main.FragmentSettings;
import com.jg.dietapp.models.DietaryTrack;
import com.jg.dietapp.models.Exercise;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.AuthPrefs;
import com.jg.dietapp.prefs.LoadPrefs;
import com.jg.dietapp.utils.ProfileUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentPlan fragmentPlan = new FragmentPlan();
    private FragmentSettings fragmentSettings = new FragmentSettings();
    private FragmentMeals fragmentMeals = new FragmentMeals();
    private Fragment activeFragment = fragmentPlan;  // Track active fragment

    private DAOPrefs daoPrefs;
    private DatabaseHelper databaseHelper;
    DietaryTrack dietaryTrack;
    private AuthPrefs authPrefs;
    private LoadPrefs loadPrefs;

    @Override
    protected void onStop() {
        super.onStop();

        // NOTE: Get Load Prefs
        int userID = authPrefs.getUserId();

        DietaryTrack currentDietaryTrack = loadPrefs.getCurrentDietaryTrack();
        int calories = currentDietaryTrack.getCalories();
        int protein = currentDietaryTrack.getProtein();
        int carbs = currentDietaryTrack.getCarbs();
        int fat = currentDietaryTrack.getFat();

        int baseCalories = loadPrefs.getBaseCalories();
        UserInput userInput = loadPrefs.getUserInput();
        List<Integer> selectedMealsID = loadPrefs.getSelectedMealsID();

        List<Meal> breakfastMeals = loadPrefs.getGeneratedBreakfastMeals();
        List<Meal> lunchMeals = loadPrefs.getGeneratedLunchMeals();
        List<Meal> dinnerMeals = loadPrefs.getGeneratedDinnerMeals();

        List<Exercise> exercises = loadPrefs.getGeneratedExercises();

        // NOTE: Save to database

        dietaryTrack = new DietaryTrack(calories, protein, carbs, fat);

        daoPrefs.insertUserInput(userID, userInput);
        daoPrefs.insertDietaryTrack(userID, dietaryTrack);
        daoPrefs.insertBaseCaloriesByUserID(userID, baseCalories);
        daoPrefs.insertSelectedMealIDs(userID, selectedMealsID);
        daoPrefs.insertGeneratedMealsByUserID(userID, breakfastMeals);
        daoPrefs.insertGeneratedMealsByUserID(userID, lunchMeals);
        daoPrefs.insertGeneratedMealsByUserID(userID, dinnerMeals);
        daoPrefs.insertGeneratedExercise(userID, exercises);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate Hello world");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        authPrefs = new AuthPrefs(this);
        loadPrefs = new LoadPrefs(this);
        databaseHelper = new DatabaseHelper(this);
        daoPrefs = new DAOPrefs(databaseHelper);
        ProfileUtils profileUtils = new ProfileUtils();
        profileUtils.getUserName();

        // Set UI
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragmentPlan, "PLAN")
                .add(R.id.fragment_container_view, fragmentSettings, "SETTINGS")
                .hide(fragmentSettings)
                .add(R.id.fragment_container_view, fragmentMeals, "MEALS")
                .hide(fragmentMeals)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                switchFragment(fragmentPlan);
            } else if (item.getItemId() == R.id.nav_settings) {
                switchFragment(fragmentSettings);
            }
            return true;
        });

    }

    private void switchFragment(Fragment targetFragment) {
        if (targetFragment != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment) // Hide current fragment
                    .show(targetFragment) // Show target fragment
                    .commit();
            activeFragment = targetFragment; // Update active fragment
        }
    }
}
