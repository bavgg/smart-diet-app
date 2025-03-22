package com.jg.dietapp.fragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.jg.dietapp.data.DAOExercise;
import com.jg.dietapp.data.DAOMeal;
import com.jg.dietapp.data.DatabaseHelper;
import com.jg.dietapp.generator.MealGenerator;
import com.jg.dietapp.models.Exercise;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.models.UserInput;
import com.jg.dietapp.prefs.ConfigurationPrefs;
import com.jg.dietapp.prefs.FirebaseDataPrefs;
import com.jg.dietapp.utils.MacronutrientCalculator;
import com.jg.dietapp.viewmodel.CurrentNutritionViewModel;
import com.jg.dietapp.viewmodel.GeneratedMealsViewModel;

import java.util.List;

public class FragmentPlan extends Fragment {

    // Initialize UI
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
    private Button regenerateButton;

    public static List<Integer> selectedMealsID;

    DAOExercise exerciseDAO;
    DAOMeal mealDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("Fragment Plan");
        super.onViewCreated(view, savedInstanceState);

        // Initialize objects
        firebaseDataPrefs = new FirebaseDataPrefs(view.getContext());
        configurationPrefs = new ConfigurationPrefs(view.getContext());
        DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
        mealDAO = new DAOMeal(dbHelper);
        exerciseDAO = new DAOExercise(dbHelper);

        // Get meals and exercises database data
        UserInput userInputs = firebaseDataPrefs.getUser();
        List<Meal> filteredMeals = mealDAO.getMealsByDietAndAllergens(userInputs);
        List<Exercise> filteredExercises = exerciseDAO.getExercisesByActivityLevel(userInputs.getActivityLevel().toString());
        firebaseDataPrefs.saveExercises(filteredExercises);


        // Generate meals data with user inputs and filtered meals database data
        MealGenerator mealGenerator = new MealGenerator(userInputs, filteredMeals);
        if(firebaseDataPrefs.getBreakfastMeals().isEmpty()) {
            mealGenerator.generateMeals();
            firebaseDataPrefs.saveGeneratedMealPlan(mealGenerator.getBreakfastMeals(), mealGenerator.getLunchMeals(), mealGenerator.getDinnerMeals(), (int) mealGenerator.getBaseCalories());
        }


        // Get generated meals data
        breakfastMeals = firebaseDataPrefs.getBreakfastMeals();
        lunchMeals = firebaseDataPrefs.getLunchMeals();
        dinnerMeals = firebaseDataPrefs.getDinnerMeals();
        baseCalories = firebaseDataPrefs.getBaseCalories();

        // Get selected meals id
        selectedMealsID = firebaseDataPrefs.getSelectedMealsID();


        // Get macros base on baseCalories
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
        regenerateButton = view.findViewById(R.id.regenerateButton);

        // Set Goals UI
        goalProteinText.setText(String.valueOf(goalProtein));
        goalCarbsText.setText(String.valueOf(goalCarbs));
        goalFatText.setText(String.valueOf(goalFat));
        goalCaloriesText.setText(String.valueOf(baseCalories));

        // Set meals UI
        breakfastAdapter = new MealAdapter(getContext(), breakfastMeals, currentNutritionViewModel, getParentFragmentManager());
        lunchAdapter = new MealAdapter(getContext(), lunchMeals, currentNutritionViewModel, getParentFragmentManager());
        dinnerAdapter = new MealAdapter(getContext(), dinnerMeals, currentNutritionViewModel, getParentFragmentManager());
        recyclerViewBreakfast = setupRecyclerView(view, R.id.recyclerViewBreakfastMeals, breakfastAdapter);
        recyclerViewLunch = setupRecyclerView(view, R.id.recyclerViewLunchMeals, lunchAdapter);
        recyclerViewDinner = setupRecyclerView(view, R.id.recyclerViewDinnerMeals, dinnerAdapter);

        // Set exercises UI
        exerciseAdapter = new ExerciseAdapter(getContext(), firebaseDataPrefs.getExercises(), getParentFragmentManager());
        recyclerViewExercises = setupRecyclerView(view, R.id.recyclerViewExercises, exerciseAdapter);

        // Regenerate button listener
        GeneratedMealsViewModel generatedMealsViewModel = new GeneratedMealsViewModel();
        regenerateButton.setOnClickListener(v -> {
            mealGenerator.generateMeals();

            List<Meal> breakfastMeals = mealGenerator.getBreakfastMeals();
            List<Meal> lunchMeals = mealGenerator.getLunchMeals();
            List<Meal> dinnerMeals = mealGenerator.getDinnerMeals();
            int baseCalories = (int) mealGenerator.getBaseCalories();

            selectedMealsID.clear();

            generatedMealsViewModel.setBreakfastMeals(breakfastMeals);
            generatedMealsViewModel.setLunchMeals(lunchMeals);
            generatedMealsViewModel.setDinnerMeals(dinnerMeals);
            generatedMealsViewModel.setBaseCalories(baseCalories);

            currentNutritionViewModel.clearNutritionData();

            firebaseDataPrefs.saveGeneratedMealPlan(mealGenerator.getBreakfastMeals(), mealGenerator.getLunchMeals(), mealGenerator.getDinnerMeals(), (int) mealGenerator.getBaseCalories());
            firebaseDataPrefs.clearSelectedMealIDs();
        });

        // Observe breakfast meals generated
        generatedMealsViewModel.getBreakfastMeals().observe(getViewLifecycleOwner(), meals -> {
            breakfastAdapter.setMeals(meals);
            breakfastAdapter.notifyDataSetChanged();
        });
        generatedMealsViewModel.getDinnerMeals().observe(getViewLifecycleOwner(), meals -> {
            lunchAdapter.setMeals(meals);
            lunchAdapter.notifyDataSetChanged();
        });
        generatedMealsViewModel.getLunchMeals().observe(getViewLifecycleOwner(), meals -> {
            dinnerAdapter.setMeals(meals);
            dinnerAdapter.notifyDataSetChanged();
        });


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
