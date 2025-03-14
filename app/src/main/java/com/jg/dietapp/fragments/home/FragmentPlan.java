package com.jg.dietapp.fragments.home;


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
import com.jg.dietapp.adapters.MealAdapter;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.generator.MealGenerator;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.shared.SharedPrefsMeals;
import com.jg.dietapp.shared.UserInput;
import com.jg.dietapp.utils.MacronutrientCalculator;
import com.jg.dietapp.viewmodel.GeneratedMealsViewModel;
import com.jg.dietapp.viewmodel.NutritionViewModel;

import java.util.Arrays;
import java.util.List;

public class FragmentPlan extends Fragment {

    private RecyclerView recyclerViewBreakfast, recyclerViewLunch, recyclerViewDinner;
    private MealAdapter breakfastAdapter, lunchAdapter, dinnerAdapter;
    private TextView currentProteinText, currentCarbsText, currentFatText, currentCaloriesText;
    private TextView goalProteinText, goalCarbsText, goalFatText, goalCaloriesText;
    private LinearProgressIndicator progressProtein, progressCarbs, progressFat;
    private CircularProgressIndicator progressCircular;

    private NutritionViewModel nutritionViewModel;
    private SharedPrefsMeals sharedPrefsMeals;

    private int baseCalories, goalProtein, goalCarbs, goalFat;
    public static List<Integer> selectedMeals;

    private List<Meal> mealOptions;
    List<Meal>[] mealsGenerated;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("FragmentPlan onCreateView Executed");
        return inflater.inflate(R.layout.fragment_plan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("FragmentPlan onViewCreated Executed");
        super.onViewCreated(view, savedInstanceState);

        System.out.println("Fragment Plan exec");

        // Initialize mealsGenerated
        GeneratedMealsViewModel generatedMealsViewModel = new ViewModelProvider(requireActivity()).get(GeneratedMealsViewModel.class);
        mealsGenerated = generatedMealsViewModel.getGeneratedMeals();

        // Initialize baseCalories
        baseCalories = generatedMealsViewModel.getBaseCalories();

        // Initialize Shared Preferences
        sharedPrefsMeals = new SharedPrefsMeals(getContext());
        selectedMeals = sharedPrefsMeals.getSelectedMeals();

        // Initialize ViewModel
        nutritionViewModel = new ViewModelProvider(requireActivity()).get(NutritionViewModel.class);

        // Initialize UI Components
        initializeUI(view);

        // Generate Meal Plan
        generateMealPlan();

        // Setup Recycler Views
        setupRecyclerViews(view);

        // Observe LiveData for Nutrition Updates
        observeNutritionData();
    }

    private void initializeUI(View view) {
        // Progress Indicators
        progressProtein = view.findViewById(R.id.progressIndicatorProtein);
        progressCarbs = view.findViewById(R.id.progressIndicatorCarbs);
        progressFat = view.findViewById(R.id.progressIndicatorFat);
        progressCircular = view.findViewById(R.id.progress_circular);

        // Current Macronutrient Text Views
        currentProteinText = view.findViewById(R.id.protein_current_text);
        currentCarbsText = view.findViewById(R.id.carbs_current_text);
        currentFatText = view.findViewById(R.id.fat_current_text);
        currentCaloriesText = view.findViewById(R.id.current_calories);

        // Goal Macronutrient Text Views
        goalProteinText = view.findViewById(R.id.protein_goal_text);
        goalCarbsText = view.findViewById(R.id.carbs_goal_text);
        goalFatText = view.findViewById(R.id.fat_goal_text);
        goalCaloriesText = view.findViewById(R.id.goal_calories);
    }

    private void generateMealPlan() {

        // Assign Meals to Lists
        List<Meal> breakfastMeals = mealsGenerated[0];
        List<Meal> lunchMeals = mealsGenerated[1];
        List<Meal> dinnerMeals = mealsGenerated[2];

        // Calculate Macronutrient Goals
        MacronutrientCalculator macronutrientCalculator = new MacronutrientCalculator(baseCalories);
        goalProtein = (int) macronutrientCalculator.getProtein();
        goalCarbs = (int) macronutrientCalculator.getCarbs();
        goalFat = (int) macronutrientCalculator.getFat();

        System.out.println(baseCalories);

        // Update UI with Goal Values
        goalProteinText.setText(String.valueOf(goalProtein));
        goalCarbsText.setText(String.valueOf(goalCarbs));
        goalFatText.setText(String.valueOf(goalFat));
        goalCaloriesText.setText(String.valueOf(baseCalories));

        // Set Adapters for Meals
        breakfastAdapter = new MealAdapter(getContext(), breakfastMeals, nutritionViewModel, this);
        lunchAdapter = new MealAdapter(getContext(), lunchMeals, nutritionViewModel, this);
        dinnerAdapter = new MealAdapter(getContext(), dinnerMeals, nutritionViewModel, this);
    }

    private void setupRecyclerViews(View view) {
        recyclerViewBreakfast = setupRecyclerView(view, R.id.recyclerViewBreakfastMeals, breakfastAdapter);
        recyclerViewLunch = setupRecyclerView(view, R.id.recyclerViewLunchMeals, lunchAdapter);
        recyclerViewDinner = setupRecyclerView(view, R.id.recyclerViewDinnerMeals, dinnerAdapter);
    }

    private RecyclerView setupRecyclerView(View view, int recyclerViewId, MealAdapter adapter) {
        RecyclerView recyclerView = view.findViewById(recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    private void observeNutritionData() {
        nutritionViewModel.getKcal().observe(getViewLifecycleOwner(), kcal -> {
            currentCaloriesText.setText(String.valueOf(kcal));
            progressCircular.setProgressCompat(getPercentage(kcal, baseCalories), true);
        });

        nutritionViewModel.getProtein().observe(getViewLifecycleOwner(), protein -> {
            currentProteinText.setText(String.valueOf(protein));
            progressProtein.setProgressCompat(getPercentage(protein, goalProtein), true);
        });

        nutritionViewModel.getCarbs().observe(getViewLifecycleOwner(), carbs -> {
            currentCarbsText.setText(String.valueOf(carbs));
            progressCarbs.setProgressCompat(getPercentage(carbs, goalCarbs), true);
        });

        nutritionViewModel.getFat().observe(getViewLifecycleOwner(), fat -> {
            currentFatText.setText(String.valueOf(fat));
            progressFat.setProgressCompat(getPercentage(fat, goalFat), true);
        });
    }

    private int getPercentage(int current, int goal) {
        return goal == 0 ? 0 : (int) ((current * 100.0) / goal);
    }
}
