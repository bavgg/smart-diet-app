package com.jg.dietapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.fragments.home.FragmentMeals;
import com.jg.dietapp.fragments.home.FragmentPlan;
import com.jg.dietapp.fragments.home.FragmentSettings;
import com.jg.dietapp.generator.MealGenerator;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.prefs.UserInputsPrefs;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.viewmodel.RecentMealsViewModel;
import com.jg.dietapp.viewmodel.GeneratedMealsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentPlan fragmentPlan = new FragmentPlan();
    private FragmentSettings fragmentSettings = new FragmentSettings();
    private FragmentMeals fragmentMeals = new FragmentMeals();
    private Fragment activeFragment = fragmentPlan;  // Track active fragment

    @Override
    public void onStart(){
        super.onStart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Fetch user input and filtered meals
        UserInputsPrefs sharedPrefsHelper = new UserInputsPrefs(this);
        UserInput userInputs = sharedPrefsHelper.getUser();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        DAOMeal mealDAO = new DAOMeal(dbHelper);
        List<Meal> filteredMeals = mealDAO.getMealsByDietAndAllergens(userInputs);

        // Fetch recent meals
        List<Meal> recentMeals = mealDAO.getRecentMeals();
        RecentMealsViewModel recentMealsViewModel = new ViewModelProvider(this).get(RecentMealsViewModel.class);
        recentMealsViewModel.setMeals(recentMeals);

        // Fetch generated meals
        MealGenerator mealGenerator = new MealGenerator(userInputs, filteredMeals);
        List<Meal>[] mealsGenerated = mealGenerator.generateMealPlan();
        GeneratedMealsViewModel generatedMealsViewModel = new ViewModelProvider(this).get(GeneratedMealsViewModel.class);
        generatedMealsViewModel.setGeneratedMeals(mealsGenerated);
        generatedMealsViewModel.setBaseCalories((int) mealGenerator.getBaseCalories());


        // Initialize bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Add fragments initially but hide settings fragment
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragmentPlan, "PLAN")
                .add(R.id.fragment_container_view, fragmentSettings, "SETTINGS")
                .hide(fragmentSettings)
                .add(R.id.fragment_container_view, fragmentMeals, "MEALS")
                .hide(fragmentMeals)
                .commit();

        // Handle bottom navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                switchFragment(fragmentPlan);
            } else if (item.getItemId() == R.id.nav_settings) {
                switchFragment(fragmentSettings);
            } else if (item.getItemId() == R.id.nav_meals) {
                switchFragment(fragmentMeals);
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
