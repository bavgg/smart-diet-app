package com.jg.dietapp.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.LoginActivity;
import com.jg.dietapp.UserInputActivity;
import com.jg.dietapp.R;
import com.jg.dietapp.data.DAOPrefs;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.models.DietaryTrack;
import com.jg.dietapp.models.Exercise;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.AuthPrefs;
import com.jg.dietapp.prefs.LoadPrefs;

import java.util.List;

public class FragmentSettings extends Fragment {
    Button clearUserDataButton, logoutButton;
    LoadPrefs loadPrefs;
    DAOPrefs daoPrefs;
    DatabaseHelper databaseHelper;
    AuthPrefs authPrefs;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clearUserDataButton = view.findViewById(R.id.clearUserDataButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        loadPrefs = new LoadPrefs(view.getContext());
        authPrefs = new AuthPrefs(view.getContext());
        databaseHelper = new DatabaseHelper(view.getContext());
        daoPrefs = new DAOPrefs(databaseHelper);

        clearUserDataButton.setOnClickListener(v -> {

            // NOTE: Clear load prefs
            loadPrefs.clearAllData();

            // NOTE: Clear prefs database
            daoPrefs.clearAllTables();

            Intent intent = new Intent(getContext(), UserInputActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {


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
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            DAOPrefs daoPrefs = new DAOPrefs(dbHelper);
            DietaryTrack dietaryTrack = new DietaryTrack(calories, protein, carbs, fat);

            daoPrefs.insertUserInput(userID, userInput);
            daoPrefs.insertDietaryTrack(userID, dietaryTrack);
            daoPrefs.insertBaseCaloriesByUserID(userID, baseCalories);
            daoPrefs.insertSelectedMealIDs(userID, selectedMealsID);
            daoPrefs.insertGeneratedMealsByUserID(userID, breakfastMeals);
            daoPrefs.insertGeneratedMealsByUserID(userID, lunchMeals);
            daoPrefs.insertGeneratedMealsByUserID(userID, dinnerMeals);
            daoPrefs.insertGeneratedExercise(userID, exercises);

            // NOTE: Clear Load Prefs
            loadPrefs.clearAllData();

            // NOTE: Clear Auth Prefs
            authPrefs.clearAuthPrefs();

            // NOTE: Redirect to login activity
            Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        });


    }


}
