package com.jg.dietapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ActivityLevelFragment extends Fragment {
    CustomCard card1, card2, card3, card4, card5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_level, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        card1 = view.findViewById(R.id.activityLevelCard1);
        card2 = view.findViewById(R.id.activityLevelCard2);
        card3 = view.findViewById(R.id.activityLevelCard3);
        card4 = view.findViewById(R.id.activityLevelCard4);
        card5 = view.findViewById(R.id.activityLevelCard5);

        setupPressBackListener();

        card1.setOnClickListener(v -> {
            card1.setCardElevation(10f);
            card2.setCardElevation(0f);
            card3.setCardElevation(0f);
            card4.setCardElevation(0f);
            card5.setCardElevation(0f);

            updateProgress();

            nextFragment(new CustomizeYourGoalFragment());
        });

        card2.setOnClickListener(v -> {
            card2.setCardElevation(10f);
            card1.setCardElevation(0f);
            card3.setCardElevation(0f);
            card4.setCardElevation(0f);
            card5.setCardElevation(0f);

            updateProgress();
            nextFragment(new CustomizeYourGoalFragment());
        });

        card3.setOnClickListener(v -> {
            card3.setCardElevation(10f);
            card2.setCardElevation(0f);
            card1.setCardElevation(0f);
            card4.setCardElevation(0f);
            card5.setCardElevation(0f);

            updateProgress();
            nextFragment(new CustomizeYourGoalFragment());
        });

        card4.setOnClickListener(v -> {
            card4.setCardElevation(10f);
            card2.setCardElevation(0f);
            card3.setCardElevation(0f);
            card1.setCardElevation(0f);
            card5.setCardElevation(0f);

            updateProgress();
            nextFragment(new CustomizeYourGoalFragment());
        });

        card5.setOnClickListener(v -> {
            card5.setCardElevation(10f);
            card2.setCardElevation(0f);
            card3.setCardElevation(0f);
            card4.setCardElevation(0f);
            card1.setCardElevation(0f);

            updateProgress();
            nextFragment(new CustomizeYourGoalFragment());
        });
    }

    private void nextFragment(CustomizeYourGoalFragment customizeYourGoalFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, customizeYourGoalFragment)
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
