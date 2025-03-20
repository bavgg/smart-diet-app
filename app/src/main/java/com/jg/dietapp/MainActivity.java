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
import com.jg.dietapp.fragments.main.FragmentMeals;
import com.jg.dietapp.fragments.main.FragmentPlan;
import com.jg.dietapp.fragments.main.FragmentSettings;
import com.jg.dietapp.generator.MealGenerator;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.utils.FirebaseUtils;
import com.jg.dietapp.viewmodel.RecentMealsViewModel;
import com.jg.dietapp.viewmodel.GeneratedPlanViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentPlan fragmentPlan = new FragmentPlan();
    private FragmentSettings fragmentSettings = new FragmentSettings();
    private FragmentMeals fragmentMeals = new FragmentMeals();
    private Fragment activeFragment = fragmentPlan;  // Track active fragment

    private DAOMeal mealDAO;
    FirebaseUtils firebaseUtils;

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume Hello world");
        firebaseUtils.loadPreferencesFromFirebase();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop Hello world");
        firebaseUtils.syncPreferencesToFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate Hello world");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        firebaseUtils = new FirebaseUtils(this);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        mealDAO = new DAOMeal(dbHelper);

        FirebaseDataPrefs firebaseDataPrefs = new FirebaseDataPrefs(this);
        UserInput userInputs = firebaseDataPrefs.getUser();
        System.out.println(userInputs);
        List<Meal> filteredMeals = mealDAO.getMealsByDietAndAllergens(userInputs);

        // Set generated meals data
        MealGenerator mealGenerator = new MealGenerator(userInputs, filteredMeals);
        GeneratedPlanViewModel generatedPlanViewModel = new ViewModelProvider(this).get(GeneratedPlanViewModel.class);
        generatedPlanViewModel.setBreakfastMeals(mealGenerator.getBreakfastMeals());
        generatedPlanViewModel.setLunchMeals(mealGenerator.getLunchMeals());
        generatedPlanViewModel.setDinnerMeals(mealGenerator.getDinnerMeals());
        generatedPlanViewModel.setBaseCalories((int) mealGenerator.getBaseCalories());
        firebaseDataPrefs.saveGeneratedMealPlan(mealGenerator.getBreakfastMeals(), mealGenerator.getLunchMeals(), mealGenerator.getDinnerMeals(), (int) mealGenerator.getBaseCalories());

        // Set recent meals data
        List<Meal> recentMeals = mealDAO.getRecentMeals();
        RecentMealsViewModel recentMealsViewModel = new ViewModelProvider(this).get(RecentMealsViewModel.class);
        recentMealsViewModel.setMeals(recentMeals);


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
