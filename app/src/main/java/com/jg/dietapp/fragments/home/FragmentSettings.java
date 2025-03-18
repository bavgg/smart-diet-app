package com.jg.dietapp.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.UserInputActivity;
import com.jg.dietapp.R;
import com.jg.dietapp.prefs.UserInputsPrefs;
import com.jg.dietapp.prefs.SelectedMealsPrefs;
import com.jg.dietapp.prefs.GoalNutrientsPrefs;

public class FragmentSettings extends Fragment {
    Button resetButton;
    UserInputsPrefs sharedUserPrefs;
    SelectedMealsPrefs selectedMealsPrefs;
    GoalNutrientsPrefs sharedPrefsNutrients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("FragmentSettings onCreateView Executed");
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("FragmentSettings onViewCreated Executed");
        super.onViewCreated(view, savedInstanceState);

        System.out.println("Fragment settings exec");
        resetButton = view.findViewById(R.id.resetButton);
        sharedUserPrefs = new UserInputsPrefs(getContext());
        selectedMealsPrefs = new SelectedMealsPrefs(getContext());
        sharedPrefsNutrients = new GoalNutrientsPrefs(getContext());


        resetButton.setOnClickListener(v -> {
            sharedUserPrefs.clearUser();
            selectedMealsPrefs.clearSelectedMeals();
            sharedPrefsNutrients.clearSelectedMeals(getContext());

            Intent intent = new Intent(getContext(), UserInputActivity.class);
            startActivity(intent);
        });

    }
}
