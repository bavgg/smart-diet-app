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
import com.jg.dietapp.enums.EnumDietType;
import com.jg.dietapp.R;

public class Fragment1eDietaryPreferences extends Fragment {
    Button continueButton;
    CustomCard vegetarianCard, omnivoreCard, halalCard, pescatarianCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dietary_preferences, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vegetarianCard = view.findViewById(R.id.vegetarianCard);
        omnivoreCard = view.findViewById(R.id.omnivoreCard);
        halalCard = view.findViewById(R.id.halalCard);
        pescatarianCard = view.findViewById(R.id.pescatarianCard);

        setupPressBackListener();

        vegetarianCard.setOnClickListener(v -> {


            userInput.setDietType(EnumDietType.Vegetarian);
            nextFragment(new Fragment1eFoodRestrictions());
            increaseProgress();
        });

        omnivoreCard.setOnClickListener(v -> {


            userInput.setDietType(EnumDietType.Omnivore);
            nextFragment(new Fragment1eFoodRestrictions());
            increaseProgress();
        });

        halalCard.setOnClickListener(v -> {


            userInput.setDietType(EnumDietType.Halal);
            nextFragment(new Fragment1eFoodRestrictions());
            increaseProgress();
        });

        pescatarianCard.setOnClickListener(v -> {


            userInput.setDietType(EnumDietType.Pescatarian);
            nextFragment(new Fragment1eFoodRestrictions());
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
