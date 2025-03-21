package com.jg.dietapp.fragments.user_input;

import static com.jg.dietapp.UserInputActivity.decreaseProgress;
import static com.jg.dietapp.UserInputActivity.increaseProgress;
import static com.jg.dietapp.UserInputActivity.userInput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jg.dietapp.components.CustomCard;
import com.jg.dietapp.enums.EnumActivityLevel;
import com.jg.dietapp.R;

public class FragmentActivityLevel extends Fragment {
    CustomCard sedentaryCard, lightlyActiveCard, moderatelyActiveCard, veryActiveCard, professionalAthleteCard;

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
            userInput.setActivityLevel(EnumActivityLevel.SEDENTARY);
            increaseProgress();
            nextFragment(new FragmentDietaryPreferences());
        });

        lightlyActiveCard.setOnClickListener(v -> {
            userInput.setActivityLevel(EnumActivityLevel.LIGHT_ACTIVITY);
            increaseProgress();
            nextFragment(new FragmentDietaryPreferences());
        });

        moderatelyActiveCard.setOnClickListener(v -> {
            userInput.setActivityLevel(EnumActivityLevel.MODERATE_ACTIVITY);
            increaseProgress();
            nextFragment(new FragmentDietaryPreferences());
        });

        veryActiveCard.setOnClickListener(v -> {
            userInput.setActivityLevel(EnumActivityLevel.HEAVY_ACTIVITY);
            increaseProgress();
            nextFragment(new FragmentDietaryPreferences());
        });

        professionalAthleteCard.setOnClickListener(v -> {
            userInput.setActivityLevel(EnumActivityLevel.EXCESSIVE_ACTIVITY);
            increaseProgress();
            nextFragment(new FragmentDietaryPreferences());
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
