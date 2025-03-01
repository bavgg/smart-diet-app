package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class CustomizeYourGoalFragment extends Fragment {
    CustomSelect currentWeightSelector, goalWeightSelector, speedSelector;
    Button createMyPlanButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customize_your_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentWeightSelector = view.findViewById(R.id.currentWeightSelector);
        goalWeightSelector = view.findViewById(R.id.goalWeightSelector);
        speedSelector = view.findViewById(R.id.speedSelector);
        createMyPlanButton = view.findViewById(R.id.createMyPlanButton);

        setupPressBackListener();

        currentWeightSelector.setOnClickListener(v -> {
            WeightDialog currentWeightDialog = new WeightDialog();
            currentWeightDialog.setOnOkClickListener(weight -> {
                currentWeightSelector.setSelectValue(weight);
                createMyPlanButton.setEnabled(isFilled());
            });
            currentWeightDialog.show(getParentFragmentManager(), "CurrentWeightDialog");
        });

        goalWeightSelector.setOnClickListener(v -> {
            WeightDialog goalWeightDialog = new WeightDialog();
            goalWeightDialog.setOnOkClickListener(weight -> {
                goalWeightSelector.setSelectValue(weight);
                createMyPlanButton.setEnabled(isFilled());
            });
            goalWeightDialog.show(getParentFragmentManager(), "GoalWeightDialog");
        });

        speedSelector.setOnClickListener(v -> {


        });

        createMyPlanButton.setOnClickListener(v -> {
            updateProgress();
            nextFragment(new YouAreAllSetFragment());
        });

    }

    private boolean isFilled() {
        String currentWeight = currentWeightSelector.getSelectValue();
        String goalWeight = goalWeightSelector.getSelectValue();
//        String height = speedSelector.getSelectValue();

        return !Objects.equals(currentWeight, "Select") &&
                !Objects.equals(goalWeight, "Select");
    }

    private void nextFragment(Fragment nextFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .addToBackStack(null) // Optional: Allows back navigation
                .commit();
    }

    private void updateProgress() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateProgress(25);
        }
    }

    private void setupPressBackListener() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateProgress(-25);
                }
                getParentFragmentManager().popBackStack();
            }
        });
    }
}
