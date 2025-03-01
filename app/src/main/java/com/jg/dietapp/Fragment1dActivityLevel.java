package com.jg.dietapp;

import static com.jg.dietapp.MainActivity.decreaseProgress;
import static com.jg.dietapp.MainActivity.increaseProgress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1dActivityLevel extends Fragment {
    CustomCard sedentaryCard, lightlyActiveCard, moderatelyActiveCard, veryActiveCard, professionalAthleteCard;
    SharedDataUser userData = MainActivity.userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_level, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        sedentaryCard = view.findViewById(R.id.sedentaryCard);
        lightlyActiveCard = view.findViewById(R.id.lightlyActiveCard);
        moderatelyActiveCard = view.findViewById(R.id.moderatelyActiveCard);
        veryActiveCard = view.findViewById(R.id.veryActiveCard);
        professionalAthleteCard = view.findViewById(R.id.professionalAthleteCard);

        setupPressBackListener();

        sedentaryCard.setOnClickListener(v -> {
            userData.setActivityLevel("sedentary");
            increaseProgress();
            nextFragment(new Fragment1eDietaryPreferences());
        });

        lightlyActiveCard.setOnClickListener(v -> {
            userData.setActivityLevel("lightly active");
            increaseProgress();
            nextFragment(new Fragment1eDietaryPreferences());
        });

        moderatelyActiveCard.setOnClickListener(v -> {
            userData.setActivityLevel("moderately active");
            increaseProgress();
            nextFragment(new Fragment1eDietaryPreferences());
        });

        veryActiveCard.setOnClickListener(v -> {
            userData.setActivityLevel("very active");
            increaseProgress();
            nextFragment(new Fragment1eDietaryPreferences());
        });

        professionalAthleteCard.setOnClickListener(v -> {
            userData.setActivityLevel("professional athlete");
            increaseProgress();
            nextFragment(new Fragment1eDietaryPreferences());
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
