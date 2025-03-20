package com.jg.dietapp.fragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.jg.dietapp.R;
import com.jg.dietapp.adapters.ExerciseAdapter;
import com.jg.dietapp.adapters.MealAdapter;
import com.jg.dietapp.enums.EnumActivityLevel;
import com.jg.dietapp.models.Exercise;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.prefs.ConfigurationPrefs;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.FirebaseUtils;
import com.jg.dietapp.utils.MacronutrientCalculator;
import com.jg.dietapp.viewmodel.GeneratedPlanViewModel;
import com.jg.dietapp.viewmodel.CurrentNutritionViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentPlan extends Fragment {

    private RecyclerView recyclerViewBreakfast, recyclerViewLunch, recyclerViewDinner, recyclerViewExercises;
    private MealAdapter breakfastAdapter, lunchAdapter, dinnerAdapter;
    private ExerciseAdapter exerciseAdapter;
    private TextView currentProteinText, currentCarbsText, currentFatText, currentCaloriesText;
    private TextView goalProteinText, goalCarbsText, goalFatText, goalCaloriesText;
    private LinearProgressIndicator progressProtein, progressCarbs, progressFat;
    private CircularProgressIndicator progressCircular;
    private CurrentNutritionViewModel currentNutritionViewModel;
    private FirebaseDataPrefs firebaseDataPrefs;
    private ConfigurationPrefs configurationPrefs;
    private int goalProtein, goalCarbs, goalFat;
    private List<Meal> breakfastMeals, lunchMeals, dinnerMeals;
    private int baseCalories;

    public static List<Integer> selectedMealsID;

    FirebaseUtils firebaseUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("Fragment Plan");
        super.onViewCreated(view, savedInstanceState);


        firebaseDataPrefs = new FirebaseDataPrefs(view.getContext());
        configurationPrefs = new ConfigurationPrefs(view.getContext());
        firebaseUtils = new FirebaseUtils(view.getContext());


//        GeneratedPlanViewModel generatedPlanViewModel = new ViewModelProvider(requireActivity()).get(GeneratedPlanViewModel.class);
//        breakfastMeals = generatedPlanViewModel.getBreakfastMeals();
//        lunchMeals = generatedPlanViewModel.getLunchMeals();
//        dinnerMeals = generatedPlanViewModel.getDinnerMeals();
//        baseCalories = generatedPlanViewModel.getBaseCalories();

        breakfastMeals = firebaseDataPrefs.getBreakfastMeals();
        lunchMeals = firebaseDataPrefs.getLunchMeals();
        dinnerMeals = firebaseDataPrefs.getDinnerMeals();
        baseCalories = firebaseDataPrefs.getBaseCalories();

        selectedMealsID = firebaseDataPrefs.getSelectedMealsID();

////        Initialized meal generation
//        if(firebaseDataPrefs.getBreakfastMeals().isEmpty()) {
//
//
//            firebaseDataPrefs.saveGeneratedMealPlan(breakfastMeals, lunchMeals, dinnerMeals, baseCalories);
//        }else {
//            breakfastMeals = firebaseDataPrefs.getBreakfastMeals();
//            lunchMeals = firebaseDataPrefs.getLunchMeals();
//            dinnerMeals = firebaseDataPrefs.getDinnerMeals();
//            baseCalories = firebaseDataPrefs.getBaseCalories();
//
////            selectedMealsID = firebaseDataPrefs.getSelectedMealsID();
//        }

        // Get current macros




        // Get macros
        MacronutrientCalculator macronutrientCalculator = new MacronutrientCalculator(baseCalories);
        goalProtein = (int) macronutrientCalculator.getProtein();
        goalCarbs = (int) macronutrientCalculator.getCarbs();
        goalFat = (int) macronutrientCalculator.getFat();

        // Initialize ViewModel
        currentNutritionViewModel = new ViewModelProvider(requireActivity()).get(CurrentNutritionViewModel.class);

        // Initialize UI Components
        progressProtein = view.findViewById(R.id.progressIndicatorProtein);
        progressCarbs = view.findViewById(R.id.progressIndicatorCarbs);
        progressFat = view.findViewById(R.id.progressIndicatorFat);
        progressCircular = view.findViewById(R.id.progress_circular);
        currentProteinText = view.findViewById(R.id.protein_current_text);
        currentCarbsText = view.findViewById(R.id.carbs_current_text);
        currentFatText = view.findViewById(R.id.fat_current_text);
        currentCaloriesText = view.findViewById(R.id.current_calories);
        goalProteinText = view.findViewById(R.id.protein_goal_text);
        goalCarbsText = view.findViewById(R.id.carbs_goal_text);
        goalFatText = view.findViewById(R.id.fat_goal_text);
        goalCaloriesText = view.findViewById(R.id.goal_calories);

        // Set Goals UI
        goalProteinText.setText(String.valueOf(goalProtein));
        goalCarbsText.setText(String.valueOf(goalCarbs));
        goalFatText.setText(String.valueOf(goalFat));
        goalCaloriesText.setText(String.valueOf(baseCalories));

        // Set recycler views
        breakfastAdapter = new MealAdapter(getContext(), breakfastMeals, currentNutritionViewModel, this);
        lunchAdapter = new MealAdapter(getContext(), lunchMeals, currentNutritionViewModel, this);
        dinnerAdapter = new MealAdapter(getContext(), dinnerMeals, currentNutritionViewModel, this);
        recyclerViewBreakfast = setupRecyclerView(view, R.id.recyclerViewBreakfastMeals, breakfastAdapter);
        recyclerViewLunch = setupRecyclerView(view, R.id.recyclerViewLunchMeals, lunchAdapter);
        recyclerViewDinner = setupRecyclerView(view, R.id.recyclerViewDinnerMeals, dinnerAdapter);
//
//        List<Exercise> exerciseList = new ArrayList<>(List.of(
//                new Exercise("Push-ups", EnumActivityLevel.HEAVY_ACTIVITY),
//                new Exercise("Squats"),
//                new Exercise("Plank")
//        ));
        // Adapter for exercise

        exerciseAdapter = new ExerciseAdapter(getContext(), firebaseDataPrefs.getExercises());
        recyclerViewExercises = setupRecyclerView(view, R.id.recyclerViewExercises, exerciseAdapter);


        // Observe nutrition data
        currentNutritionViewModel.getCurrentKcal().observe(getViewLifecycleOwner(), kcal -> {
            currentCaloriesText.setText(String.valueOf(kcal));
            progressCircular.setProgressCompat(getPercentage(kcal, baseCalories), true);
        });

        currentNutritionViewModel.getCurrentProtein().observe(getViewLifecycleOwner(), protein -> {
            currentProteinText.setText(String.valueOf(protein));
            progressProtein.setProgressCompat(getPercentage(protein, goalProtein), true);
        });

        currentNutritionViewModel.getCurrentCarbs().observe(getViewLifecycleOwner(), carbs -> {
            currentCarbsText.setText(String.valueOf(carbs));
            progressCarbs.setProgressCompat(getPercentage(carbs, goalCarbs), true);
        });

        currentNutritionViewModel.getCurrentFat().observe(getViewLifecycleOwner(), fat -> {
            currentFatText.setText(String.valueOf(fat));
            progressFat.setProgressCompat(getPercentage(fat, goalFat), true);
        });
    }

    private RecyclerView setupRecyclerView(View view, int recyclerViewId, ExerciseAdapter adapter) {
        RecyclerView recyclerView = view.findViewById(recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }
    private RecyclerView setupRecyclerView(View view, int recyclerViewId, MealAdapter adapter) {
        RecyclerView recyclerView = view.findViewById(recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    private int getPercentage(int current, int goal) {
        return goal == 0 ? 0 : (int) ((current * 100.0) / goal);
    }
}
