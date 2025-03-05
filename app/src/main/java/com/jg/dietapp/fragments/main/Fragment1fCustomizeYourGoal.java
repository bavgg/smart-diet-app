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

import com.jg.dietapp.components.CustomSelect;
import com.jg.dietapp.dialogs.DialogWeight;
import com.jg.dietapp.R;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Fragment1fCustomizeYourGoal extends Fragment {
    CustomSelect currentWeightSelector, goalWeightSelector;
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
        createMyPlanButton = view.findViewById(R.id.createMyPlanButton);

        AtomicReference<Double> goalWeightInfo = new AtomicReference<>((double) 0);

        setupPressBackListener();


        currentWeightSelector.setSelectValue(String.valueOf(userInput.getWeight()));

//        currentWeightSelector.setOnClickListener(v -> {
//            DialogWeight currentWeightDialog = new DialogWeight();
//            currentWeightDialog.setOnOkClickListener((weight, unit) -> {
//                currentWeightSelector.setSelectValue(weight + " " + unit);
//                createMyPlanButton.setEnabled(isFilled());
//            });
//            currentWeightDialog.show(getParentFragmentManager(), "CurrentWeightDialog");
//        });

        goalWeightSelector.setOnClickListener(v -> {
            DialogWeight goalWeightDialog = new DialogWeight();
            goalWeightDialog.setOnOkClickListener((weight, unit) -> {
                goalWeightSelector.setSelectValue(weight + " " + unit);
                createMyPlanButton.setEnabled(isFilled());

                goalWeightInfo.set(weight);
            });
            goalWeightDialog.show(getParentFragmentManager(), "GoalWeightDialog");
        });



        createMyPlanButton.setOnClickListener(v -> {
            String goalWeight = goalWeightSelector.getSelectValue();


            userInput.setGoalWeight(goalWeightInfo.get());

            increaseProgress();
            nextFragment(new Fragment1gYouAreAllSet());
        });

    }

    private boolean isFilled() {
        String currentWeight = currentWeightSelector.getSelectValue();
        String goalWeight = goalWeightSelector.getSelectValue();

        return !Objects.equals(currentWeight, "Select") &&
                !Objects.equals(goalWeight, "Select");
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
