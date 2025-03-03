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

public class Fragment1bGoal extends Fragment {
    CustomCard fatLossCard, muscleGainCard, weightMaintenanceCard;
    ModelUserInput userInput = MainActivity.userInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fatLossCard = view.findViewById(R.id.fatLossCard);
        muscleGainCard = view.findViewById(R.id.muscleGainCard);
        weightMaintenanceCard = view.findViewById(R.id.weightMaintenanceCard);

        setupBackPressedListener();

        fatLossCard.setOnClickListener(v -> {
            userInput.setGoal(EnumGoal.LOSE_WEIGHT);
            increaseProgress();
            fatLossCard.setCardElevation(10f);

            nextFragment(new Fragment1cAboutYou());
        });

        muscleGainCard.setOnClickListener(v -> {
            userInput.setGoal(EnumGoal.GAIN_MUSCLE);
            increaseProgress();
            muscleGainCard.setCardElevation(10f);

            nextFragment(new Fragment1cAboutYou());
        });

        weightMaintenanceCard.setOnClickListener(v -> {
            userInput.setGoal(EnumGoal.MAINTAIN_WEIGHT);
            increaseProgress();
            weightMaintenanceCard.setCardElevation(10f);

            nextFragment(new Fragment1cAboutYou());
        });

    }

    private void nextFragment(Fragment nextFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupBackPressedListener() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                decreaseProgress();
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
