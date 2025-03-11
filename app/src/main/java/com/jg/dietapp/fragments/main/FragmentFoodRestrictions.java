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

public class FragmentFoodRestrictions extends Fragment {
    CustomCard soyCard, glutenCard, eggCard, fishCard;
    Button continueButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_restrictions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        soyCard = view.findViewById(R.id.soyCard);
        glutenCard = view.findViewById(R.id.glutenCard);
        eggCard = view.findViewById(R.id.eggCard);
        fishCard = view.findViewById(R.id.fishCard);

        continueButton = view.findViewById(R.id.continueButton);
        AtomicReference<String> foodAllergens = new AtomicReference<>("");

        setupPressBackListener();

        soyCard.setOnClickListener(v -> {
            boolean isSelected = soyCard.isSelected();

            soyCard.setSelected(!isSelected);

            if (!isSelected) {
                soyCard.setElevation(10f);
                soyCard.setSelected(true);
            } else {
                soyCard.setElevation(0f);
                soyCard.setSelected(false);
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

        eggCard.setOnClickListener(v -> {
            boolean isSelected = eggCard.isSelected();

            eggCard.setSelected(!isSelected);

            if (!isSelected) {
                eggCard.setElevation(10f);
                eggCard.setSelected(true);
            } else {
                eggCard.setElevation(0f);
                eggCard.setSelected(false);
            }
        });

        fishCard.setOnClickListener(v -> {
            boolean isSelected = fishCard.isSelected();

            fishCard.setSelected(!isSelected);

            if (!isSelected) {
                fishCard.setElevation(10f);
                fishCard.setSelected(true);
            } else {
                fishCard.setElevation(0f);
                fishCard.setSelected(false);
            }
        });

        continueButton.setOnClickListener(v -> {
            if (soyCard.isSelected()) {
                foodAllergens.updateAndGet(value -> value + ",Soy");
            }
            if (glutenCard.isSelected()) {
                foodAllergens.updateAndGet(value -> value + ",Gluten");
            }
            if (eggCard.isSelected()) {
                foodAllergens.updateAndGet(value -> value + ",Egg");
            }
            if (fishCard.isSelected()) {
                foodAllergens.updateAndGet(value -> value + ",Fish");
            }

            userInput.setFoodAllergens(foodAllergens.get());
            nextFragment(new FragmentYouAreAllSet());
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


