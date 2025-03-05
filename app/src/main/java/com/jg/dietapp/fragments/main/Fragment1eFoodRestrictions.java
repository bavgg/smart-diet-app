package com.jg.dietapp.fragments.main;

import static com.jg.dietapp.MainActivity.decreaseProgress;
import static com.jg.dietapp.MainActivity.increaseProgress;
import static com.jg.dietapp.MainActivity.userInput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.components.CustomCard;
import com.jg.dietapp.R;

import java.util.concurrent.atomic.AtomicReference;

public class Fragment1eFoodRestrictions extends Fragment {
    CustomCard nutsCard, lactoseCard, glutenCard;
    Button continueButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_restrictions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nutsCard = view.findViewById(R.id.nutsCard);
        lactoseCard = view.findViewById(R.id.lactoseCard);
        glutenCard = view.findViewById(R.id.glutenCard);

        continueButton = view.findViewById(R.id.continueButton);
        AtomicReference<String> foodAllergens = new AtomicReference<>("");

        setupPressBackListener();

        nutsCard.setOnClickListener(v -> {
            boolean isSelected = nutsCard.isSelected();

            nutsCard.setSelected(!isSelected);

            if (!isSelected) {
                nutsCard.setElevation(10f);
                nutsCard.setSelected(true);
            } else {
                nutsCard.setElevation(0f);
                nutsCard.setSelected(false);
            }
        });

        lactoseCard.setOnClickListener(v -> {
            boolean isSelected = lactoseCard.isSelected();

            lactoseCard.setSelected(!isSelected);

            if (!isSelected) {
                lactoseCard.setElevation(10f);
                lactoseCard.setSelected(true);
            } else {
                lactoseCard.setElevation(0f);
                lactoseCard.setSelected(false);
            }
        });

        glutenCard.setOnClickListener(v -> {
            boolean isSelected = glutenCard.isSelected();

            glutenCard.setSelected(!isSelected);

            if (!isSelected) {
                glutenCard.setElevation(10f);
                glutenCard.setSelected(true);
            } else {
                glutenCard.setElevation(0f);
                glutenCard.setSelected(false);
            }
        });

        continueButton.setOnClickListener(v -> {
            if (nutsCard.isSelected()) {
                foodAllergens.updateAndGet(value -> value + ",Nuts");
            }
            if (lactoseCard.isSelected()) {
                foodAllergens.updateAndGet(value -> value + ",Lactose");
            }
            if (glutenCard.isSelected()) {
                foodAllergens.updateAndGet(value -> value + ",Gluten");
            }

            userInput.setFoodAllergens(foodAllergens.get());
            nextFragment(new Fragment1fCustomizeYourGoal());
            increaseProgress();
        });
    }
    private void nextFragment(Fragment nextFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupPressBackListener() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                decreaseProgress();
                getParentFragmentManager().popBackStack();
            }
        });
    }
}


