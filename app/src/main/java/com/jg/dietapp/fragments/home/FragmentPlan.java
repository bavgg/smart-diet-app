package com.jg.dietapp.fragments.home;

import static com.jg.dietapp.MainActivity.sharedPrefsHelper;
//import static com.jg.dietapp.MainActivity.userInput;

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
import com.jg.dietapp.generator.GeneratorMeal;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.shared.SharedPrefsMeals;
import com.jg.dietapp.shared.UserInput;
import com.jg.dietapp.utils.MacronutrientCalculator;
import com.jg.dietapp.viewmodel.NutritionViewModel;

import java.util.List;

public class FragmentPlan extends Fragment {
    private RecyclerView recyclerViewBreakfastMeals, recyclerViewLunchMeals, recyclerViewDinnerMeals;
    private MealAdapter breakfastMealsAdapter, lunchMealsAdapter, dinnerMealsAdapter;
    private UserInput userInput;
    private TextView currentProteinText, currentCarbsText, currentFatText, currentCaloriesText;
    private TextView goalProteinText, goalCarbsText, goalFatText, goalCaloriesText;
    private LinearProgressIndicator progressIndicatorProtein, progressIndicatorCarbs, progressIndicatorFat;

    private List<Meal>[] meals;
    private GeneratorMeal generatorMeal;
    private NutritionViewModel nutritionViewModel;
    private List<Meal> breakfastMeals, lunchMeals, dinnerMeals;
    private int baseCalories;
    private MacronutrientCalculator macronutrientCalculator;
    public static List<Integer> selectedMeals;
    private SharedPrefsMeals sharedPrefsMeals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.sharedPrefsMeals = new SharedPrefsMeals(getContext());
        selectedMeals = sharedPrefsMeals.getSelectedMeals();

        super.onViewCreated(view, savedInstanceState);
        userInput = sharedPrefsHelper.getUser(getContext());
        generatorMeal = new GeneratorMeal(userInput);
        meals = generatorMeal.generateMealPlan(getContext());
        nutritionViewModel = new ViewModelProvider(this).get(NutritionViewModel.class);

        breakfastMeals = meals[0];
        lunchMeals = meals[1];
        dinnerMeals = meals[2];

        baseCalories = (int) (generatorMeal.getBaseCalories());
        macronutrientCalculator = new MacronutrientCalculator(baseCalories);
        int protein = (int) (macronutrientCalculator.getProtein());
        int carbs = (int) (macronutrientCalculator.getCarbs());
        int fat = (int) (macronutrientCalculator.getFat());

        progressIndicatorProtein = view.findViewById(R.id.progressIndicatorProtein);
        progressIndicatorCarbs = view.findViewById(R.id.progressIndicatorCarbs);
        progressIndicatorFat = view.findViewById(R.id.progressIndicatorFat);

        currentProteinText = view.findViewById(R.id.protein_current_text);
        currentCarbsText = view.findViewById(R.id.carbs_current_text);
        currentFatText = view.findViewById(R.id.fat_current_text);
        currentCaloriesText = view.findViewById(R.id.current_calories);

        goalProteinText = view.findViewById(R.id.protein_goal_text);
        goalCarbsText = view.findViewById(R.id.carbs_goal_text);
        goalFatText = view.findViewById(R.id.fat_goal_text);
        goalCaloriesText = view.findViewById(R.id.goal_calories);

        String proteinText = protein + "";
        String carbsText = carbs + "";
        String fatText = fat + "";

        goalProteinText.setText(proteinText);
        goalCarbsText.setText(carbsText);
        goalFatText.setText(fatText);
        goalCaloriesText.setText(baseCalories + "");

        // Circular progress bar
        CircularProgressIndicator circularProgressIndicator = view.findViewById(R.id.progress_circular);

        // Observe LiveData
        nutritionViewModel.getKcal().observe(getViewLifecycleOwner(), kcalR -> {
            currentCaloriesText.setText(kcalR + "");
            int progress = (int) ((kcalR * 100) / baseCalories); // Convert to percentage
            circularProgressIndicator.setProgressCompat(progress, true);
        });

        nutritionViewModel.getProtein().observe(getViewLifecycleOwner(), proteinR -> {
            currentProteinText.setText(proteinR + "");
            int progress = (int) ((proteinR * 100) / protein);
            progressIndicatorProtein.setProgressCompat(progress, true);
        });

        nutritionViewModel.getCarbs().observe(getViewLifecycleOwner(), carbsR -> {
            currentCarbsText.setText(carbsR + "");
            int progress = (int) ((carbsR * 100) / carbs);
            progressIndicatorCarbs.setProgressCompat(progress, true);
        });

        nutritionViewModel.getFat().observe(getViewLifecycleOwner(), fatR -> {
            currentFatText.setText(fatR + "");
            int progress = (int) ((fatR * 100) / fat);
            progressIndicatorFat.setProgressCompat(progress, true);
        });

        setAdapters(view);
    }

    private void setAdapters(View view) {
        // Breakfast
        recyclerViewBreakfastMeals = view.findViewById(R.id.recyclerViewBreakfastMeals);
        recyclerViewBreakfastMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        breakfastMealsAdapter = new MealAdapter(getContext(), breakfastMeals, nutritionViewModel, this);
        recyclerViewBreakfastMeals.setAdapter(breakfastMealsAdapter);

        // Lunch
        recyclerViewLunchMeals = view.findViewById(R.id.recyclerViewLunchMeals);
        recyclerViewLunchMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        lunchMealsAdapter = new MealAdapter(getContext(), lunchMeals, nutritionViewModel, this);
        recyclerViewLunchMeals.setAdapter(lunchMealsAdapter);

        // Dinner
        recyclerViewDinnerMeals = view.findViewById(R.id.recyclerViewDinnerMeals);
        recyclerViewDinnerMeals.setLayoutManager(new LinearLayoutManager(getContext()));
        dinnerMealsAdapter = new MealAdapter(getContext(), dinnerMeals, nutritionViewModel, this);
        recyclerViewDinnerMeals.setAdapter(dinnerMealsAdapter);
    }


}
