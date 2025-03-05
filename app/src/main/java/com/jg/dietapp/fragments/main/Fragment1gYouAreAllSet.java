package com.jg.dietapp.fragments.main;

import static com.jg.dietapp.MainActivity.sharedPrefsHelper;
import static com.jg.dietapp.MainActivity.userInput;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.generator.GeneratorMeal;
import com.jg.dietapp.HomeActivity;
import com.jg.dietapp.models.Meal;
import com.jg.dietapp.R;

import java.util.List;

public class Fragment1gYouAreAllSet extends Fragment {

    Button nextButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_you_are_all_set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextButton = view.findViewById(R.id.nextButton);
        GeneratorMeal generatorMeal = new GeneratorMeal(userInput);
        List<Meal> meals = generatorMeal.generateMealPlan(getContext());

        userInput.setUserSubmitted(true);

        sharedPrefsHelper.saveUser(getContext(), userInput);


        System.out.println(meals);
        System.out.println("Calories: " + generatorMeal.getBaseCalories());

//        for (ModelMeal meal : meals) {
//            System.out.println("Meal: " + meal.getName() + ", Calories: " + meal.getCalories());
//        }

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        });


        System.out.println(userInput);

    }
}
