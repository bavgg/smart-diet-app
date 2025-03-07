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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.jg.dietapp.R;
import com.jg.dietapp.adapters.MealAdapter;
import com.jg.dietapp.components.CustomMealList;
import com.jg.dietapp.generator.GeneratorMeal;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.shared.UserInput;
import com.jg.dietapp.utils.MacronutrientCalculator;

import java.util.List;

public class FragmentPlan extends Fragment {
    private RecyclerView recyclerViewBreakfastMeals, recyclerViewLunchMeals, recyclerViewDinnerMeals;
    private MealAdapter breakfastMealsAdapter, lunchMealsAdapter, dinnerMealsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInput userInput = sharedPrefsHelper.getUser(getContext());
        GeneratorMeal generatorMeal = new GeneratorMeal(userInput);
        List<Meal>[] meals = generatorMeal.generateMealPlan(getContext());

        List<Meal> breakfastMeals = meals[0];
        List<Meal> lunchMeals = meals[1];
        List<Meal> dinnerMeals = meals[2];

        int baseCalories = (int) (generatorMeal.getBaseCalories());

        MacronutrientCalculator macronutrientCalculator = new MacronutrientCalculator(baseCalories);
        int protein = (int) (macronutrientCalculator.getProtein());
        int carbs = (int) (macronutrientCalculator.getCarbs());
        int fat = (int) (macronutrientCalculator.getFat());

        TextView currentProteinText = view.findViewById(R.id.protein_current_text);
        TextView currentCarbsText = view.findViewById(R.id.carbs_current_text);
        TextView currentFatText = view.findViewById(R.id.fat_current_text);

        TextView goalProteinText = view.findViewById(R.id.protein_goal_text);
        TextView goalCarbsText = view.findViewById(R.id.carbs_goal_text);
        TextView goalFatText = view.findViewById(R.id.fat_goal_text);

        String proteinText = protein + "";
        String carbsText = carbs + "";
        String fatText = fat + "";

        goalProteinText.setText(proteinText);
        goalCarbsText.setText(carbsText);
        goalFatText.setText(fatText);

        System.out.println(protein);
        System.out.println(carbs);
        System.out.println(fat);


        // Circular progress bar
        CircularProgressIndicator circularProgressIndicator = view.findViewById(R.id.progress_circular);
        double completedCalories = 250;

        int progress = (int) ((completedCalories * 100) / baseCalories); // Convert to percentage
        circularProgressIndicator.setProgressCompat(progress, true);

        TextView currentCaloriesText = view.findViewById(R.id.current_calories);
        TextView goalCaloriesText = view.findViewById(R.id.goal_calories);
        goalCaloriesText.setText(baseCalories + "");
        System.out.println("BASE: " + baseCalories);



        // Breakfast
        recyclerViewBreakfastMeals = view.findViewById(R.id.recyclerViewBreakfastMeals);
        recyclerViewBreakfastMeals.setLayoutManager(new LinearLayoutManager(getContext()));


        breakfastMealsAdapter = new MealAdapter(breakfastMeals);
        recyclerViewBreakfastMeals.setAdapter(breakfastMealsAdapter);

        // Lunch
        recyclerViewLunchMeals = view.findViewById(R.id.recyclerViewLunchMeals);
        recyclerViewLunchMeals.setLayoutManager(new LinearLayoutManager(getContext()));


        lunchMealsAdapter = new MealAdapter(lunchMeals);
        recyclerViewLunchMeals.setAdapter(lunchMealsAdapter);

        // Dinner
        recyclerViewDinnerMeals = view.findViewById(R.id.recyclerViewDinnerMeals);
        recyclerViewDinnerMeals.setLayoutManager(new LinearLayoutManager(getContext()));


        dinnerMealsAdapter = new MealAdapter(dinnerMeals);
        recyclerViewDinnerMeals.setAdapter(dinnerMealsAdapter);


        double breakfastTotalCal = 0;

        for(Meal meal : meals[0]){
            breakfastTotalCal += meal.getCalories();
        }

        double lunchTotalCal = 0;

        for(Meal meal : meals[1]){
            lunchTotalCal += meal.getCalories();
        }

        double dinnerTotalCal = 0;

        for(Meal meal : meals[2]){
            dinnerTotalCal += meal.getCalories();
        }

        System.out.println("Breakfast: "+ breakfastTotalCal + " Calories :" + meals[0]);
        System.out.println("Lunch: "+ lunchTotalCal + " Calories :"  + meals[1]);
        System.out.println("Dinner: "+ dinnerTotalCal + " Calories :"  + meals[2]);

//        System.out.println(meals);
//
//
//
//        if (meals.size() >= 3) {
//            // Distribute meals across meal1, meal2, and meal3
//            int index = 0;
//
//            // Assign breakfast meals
//            StringBuilder breakfastMeals = new StringBuilder("Breakfast:\n");
//            while (index < meals.size() / 3) {
//                breakfastMeals.append(meals.get(index).getName()).append("\n");
//                index++;
//            }
//            meal1.setMealName(breakfastMeals.toString());
//
//            // Assign lunch meals
//            StringBuilder lunchMeals = new StringBuilder("Lunch:\n");
//            while (index < (2 * meals.size()) / 3) {
//                lunchMeals.append(meals.get(index).getName()).append("\n");
//                index++;
//            }
//            meal2.setMealName(lunchMeals.toString());
//
//            // Assign dinner meals
//            StringBuilder dinnerMeals = new StringBuilder("Dinner:\n");
//            while (index < meals.size()) {
//                dinnerMeals.append(meals.get(index).getName()).append("\n");
//                index++;
//            }
//            meal3.setMealName(dinnerMeals.toString());
//        }
//        for (Meal meal : meals) {
//            meal1.setMea(meal.getName());
//            System.out.println("Meal: " + meal.getName() + ", Calories: " + meal.getCalories());
//        }

//        System.out.println(meals);
//        System.out.println("Calories: " + generatorMeal.getBaseCalories());
    }
}
