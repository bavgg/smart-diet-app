package com.jg.dietapp.fragments.home;

import static com.jg.dietapp.MainActivity.sharedPrefsHelper;
import static com.jg.dietapp.MainActivity.userInput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.R;
import com.jg.dietapp.components.CustomMealList;
import com.jg.dietapp.generator.GeneratorMeal;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.shared.UserInput;

import java.util.List;

public class FragmentPlan extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomMealList meal1 = view.findViewById(R.id.meal_list1);
        CustomMealList meal2 = view.findViewById(R.id.meal_list2);
        CustomMealList meal3 = view.findViewById(R.id.meal_list3);
        meal1.setOnClickListener(v -> {
            meal1.toggleRadioButton();
        });

        UserInput userInput = sharedPrefsHelper.getUser(getContext());

        GeneratorMeal generatorMeal = new GeneratorMeal(userInput);
        List<Meal>[] meals = generatorMeal.generateMealPlan(getContext());

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
